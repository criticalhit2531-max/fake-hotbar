# Fake Hotbar

Client-side Fabric mod for Minecraft Java 1.21.11.

## What it does

The nine hotbar item icons are drawn in this left-to-right order:

`4 5 3 1 6 8 7 2 9`

Only the displayed item stacks are reordered. The actual inventory slots, number-key selection, selected-slot highlight, and held item are not intentionally changed.

## Build

Requirements:
- Java 21 JDK
- Gradle 9.2 or newer (or import this project into an IDE with Gradle support)
- Internet access for Gradle to download Minecraft/Fabric build dependencies

From this folder, run:

- Windows: `gradle build`
- macOS/Linux: `gradle build`

The remapped mod JAR should be created in `build/libs/`.

Copy the non-`-sources` JAR from `build/libs/` into your Minecraft `mods` folder. Install Fabric Loader 0.18.1 or newer for Minecraft 1.21.11.

## Notes

This is a source project, not a precompiled JAR. The Mixin targets the 1.21.11 Yarn mapping for `InGameHud.renderHotbar`; test it in a separate game instance before using it with other HUD mods.
