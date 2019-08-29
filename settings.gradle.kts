rootProject.name = "gdxmachine-root"

include(
        "gdxmachine-core",
        "gdxmachine-platform-desktop",
        "gdxmachine-platform-android",
        "gdxmachine-sandbox-desktop",
        "gdxmachine-sandbox-android",
        "gdxmachine-sandbox-source"
)

project(":gdxmachine-core").name = "core"
project(":gdxmachine-platform-desktop").name = "platform-desktop"
project(":gdxmachine-platform-android").name = "platform-android"
project(":gdxmachine-sandbox-desktop").name = "sandbox-desktop"
project(":gdxmachine-sandbox-android").name = "sandbox-android"
project(":gdxmachine-sandbox-source").name = "sandbox-source"