

# dali pixi

- pixi.js webgl 

- on chrome : pixi.cljs:16 Failed to create WebGPU Context Provider
  - /gnu/store/m20wfrjnnnf6912yxw1mzm7bbqwzxgfq-google-chrome-beta-130.0.6723.19/bin/google-chrome-beta ogle-chrome-beta --enable-unsafe-webgpu --enable-features=Vulkan
  - Enable WebGPU hardware acceleration at chrome://flags/#enable-webgpu-developer-features
  - Launch chrome using: google-chrome --enable-unsafe-webgpu --enable-features=Vulkan,UseSkiaRenderer as per
    https://stackoverflow.com/questions/72294876/i-enable-webgpu-in-chrome-dev-and-it-still-doesnt-work
  - https://webgpu.github.io/webgpu-samples/?sample=instancedCube