{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = { nixpkgs, ... }:
  
  let 
    pkgs = nixpkgs.legacyPackages.x86_64-linux;
    libs = with pkgs; [
        libpulseaudio
        libGL
        glfw
        openal
        jdk21
        checkstyle
        google-java-format
      ];
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      name = "fabricmc";
      packages = [];
      buildInputs = libs;
      shellHook = ''
        exec nu
      '';
      LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath libs;
    };
  };
}
