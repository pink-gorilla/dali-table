const canvas = document.getElementById('glCanvas');
const gl = canvas.getContext('webgl');
const scrollBar = document.getElementById('scrollBar');

if (!gl) {
    console.error("WebGL isn't supported on this browser.");
}

// Example: Large dataset with many candles (over 1000)
const candles = [];
for (let i = 0; i < 1000; i++) {
    const open = Math.random() * 100 + 100;
    const close = open + (Math.random() * 10 - 5);
    const high = Math.max(open, close) + Math.random() * 10;
    const low = Math.min(open, close) - Math.random() * 10;
    candles.push({ open, high, low, close, date: `Day ${i + 1}` });
}

// Config
let visibleBars = 200; // Show 200 candles at a time (or adjust dynamically)
let scrollIndex = 0; // Start at the beginning
const candleWidth = 10; // Fixed width for each candle
let maxScrollIndex = candles.length - visibleBars;

const CHART_HEIGHT_RATIO = 0.8; // 80% for the chart
const SIN_HEIGHT_RATIO = 0.2;   // 20% for the sinusoidal wave

// Initialize WebGL
canvas.width = window.innerWidth;
canvas.height = window.innerHeight - 30; // Adjust for scrollbar height
gl.viewport(0, 0, canvas.width, canvas.height);

// Set the clear color to black and clear the canvas
gl.clearColor(0.0, 0.0, 0.0, 1.0);
gl.clear(gl.COLOR_BUFFER_BIT);

// Define shaders
const vertexShaderSource = `
  attribute vec2 aPosition;
  uniform vec2 uResolution;
  void main() {
    vec2 zeroToOne = aPosition / uResolution;
    vec2 zeroToTwo = zeroToOne * 2.0;
    vec2 clipSpace = zeroToTwo - 1.0;
    gl_Position = vec4(clipSpace * vec2(1, -1), 0, 1);
  }
`;

const fragmentShaderSource = `
  precision mediump float;
  uniform vec4 uColor;
  void main() {
    gl_FragColor = uColor;
  }
`;

// Create and compile shaders
function createShader(gl, type, source) {
    const shader = gl.createShader(type);
    gl.shaderSource(shader, source);
    gl.compileShader(shader);
    if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
        console.error(gl.getShaderInfoLog(shader));
        gl.deleteShader(shader);
        return null;
    }
    return shader;
}

const vertexShader = createShader(gl, gl.VERTEX_SHADER, vertexShaderSource);
const fragmentShader = createShader(gl, gl.FRAGMENT_SHADER, fragmentShaderSource);

// Create the program
function createProgram(gl, vertexShader, fragmentShader) {
    const program = gl.createProgram();
    gl.attachShader(program, vertexShader);
    gl.attachShader(program, fragmentShader);
    gl.linkProgram(program);
    if (!gl.getProgramParameter(program, gl.LINK_STATUS)) {
        console.error(gl.getProgramInfoLog(program));
        gl.deleteProgram(program);
        return null;
    }
    return program;
}

const program = createProgram(gl, vertexShader, fragmentShader);

// Lookup locations
const positionLocation = gl.getAttribLocation(program, 'aPosition');
const resolutionUniformLocation = gl.getUniformLocation(program, 'uResolution');
const colorUniformLocation = gl.getUniformLocation(program, 'uColor');

// Create a buffer
const positionBuffer = gl.createBuffer();
gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);

// Enable the attribute and set its pointer
gl.enableVertexAttribArray(positionLocation);
gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);

// Set resolution uniform
gl.useProgram(program);
gl.uniform2f(resolutionUniformLocation, gl.canvas.width, gl.canvas.height);

// Function to dynamically calculate visible bars based on canvas width
function calculateVisibleBars() {
    const availableWidth = canvas.width;
    visibleBars = Math.floor(availableWidth / candleWidth);
    maxScrollIndex = candles.length - visibleBars;
    scrollBar.max = maxScrollIndex;
}

// Draw candles
function drawCandle(candle, x) {
    const { open, high, low, close } = candle;
    const wickWidth = 2;

    const color = open < close ? [0, 1, 0, 1] : [1, 0, 0, 1];
    gl.uniform4fv(colorUniformLocation, color);

    // Candle body
    const body = [
        x - candleWidth / 2, open,
        x + candleWidth / 2, open,
        x + candleWidth / 2, close,
        x - candleWidth / 2, close
    ];

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(body), gl.STATIC_DRAW);
    gl.drawArrays(gl.TRIANGLE_FAN, 0, 4);

    // Wick
    gl.uniform4fv(colorUniformLocation, [1, 1, 1, 1]); // Set wick color to white
    const wick = [
        x - wickWidth / 2, high,
        x + wickWidth / 2, high,
        x + wickWidth / 2, low,
        x - wickWidth / 2, low
    ];

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(wick), gl.STATIC_DRAW);
    gl.drawArrays(gl.TRIANGLE_FAN, 0, 4);
}

// Draw sine wave
function drawSineWave() {
    const sineWave = [];
    const amplitude = 30; // Height of the wave
    const frequency = 0.05; // Frequency of the wave

    for (let x = 0; x < canvas.width; x += 5) {
        const y = Math.sin(x * frequency) * amplitude + canvas.height * CHART_HEIGHT_RATIO + amplitude; // Position it below chart
        sineWave.push(x, y);
    }

    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(sineWave), gl.STATIC_DRAW);
    gl.uniform4fv(colorUniformLocation, [0, 0, 1, 1]); // Blue color for sine wave
    gl.drawArrays(gl.LINE_STRIP, 0, sineWave.length / 2);
}

// Render function
function render() {
    gl.clear(gl.COLOR_BUFFER_BIT);

    // Chart Section (80%)
    gl.viewport(0, canvas.height * SIN_HEIGHT_RATIO, canvas.width, canvas.height * CHART_HEIGHT_RATIO);
    gl.uniform2f(resolutionUniformLocation, gl.canvas.width, gl.canvas.height);
    const visibleCandles = candles.slice(scrollIndex, scrollIndex + visibleBars);
    const candleSpacing = candleWidth + 5; // Add some spacing between candles

    visibleCandles.forEach((candle, i) => {
        const x = i * candleSpacing + candleSpacing / 2;
        drawCandle(candle, x);
    });

    // Sinusoidal Wave Section (20%)
    gl.viewport(0, 0, canvas.width, canvas.height * SIN_HEIGHT_RATIO);
    drawSineWave();
}

// Handle scrollbar change
scrollBar.addEventListener('input', (event) => {
    scrollIndex = parseInt(event.target.value);
    render();
});

// Initialize scrollbar max value
scrollBar.max = maxScrollIndex;
scrollBar.value = scrollIndex;

// Initial render
calculateVisibleBars();
render();

// Resize handler
window.addEventListener('resize', () => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight - 30; // Adjust for scrollbar
    gl.viewport(0, 0, canvas.width, canvas.height);
    calculateVisibleBars();
    render();
});

