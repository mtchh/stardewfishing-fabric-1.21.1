# Stardew Fishing (Fabric)

**I vibe coded this for my gf**

[![Modrinth](https://img.shields.io/modrinth/dt/stardew-fishing-fabric?logo=modrinth&label=modrinth&color=1bd96a)](https://modrinth.com/mod/stardew-fishing-fabric)
[![Minecraft](https://img.shields.io/badge/minecraft-1.21.1-brightgreen.svg)](https://minecraft.net)
[![Fabric API](https://img.shields.io/badge/fabric--api-0.116.5%2B1.21.1-blue.svg)](https://fabricmc.net)

## 📦 Installation

The .jar file to put in your mods folder is under build/libs/stardewfishing-fabric-1.3.1.jar

## ⚙️ Configuration

The mod uses a data-driven configuration system via `data.json`. You can customize:

### Fish Behavior Settings

- **`idle_time`**: How long fish stays still (higher = easier)
- **`top_speed`**: Fish movement speed (lower = easier)
- **`up_acceleration`** & **`down_acceleration`**: Movement acceleration
- **`avg_distance`**: Distance fish travels (lower = easier)
- **`move_variation`**: Movement randomness (lower = more predictable)

### Example Configuration

```json
{
  "behaviors": {
    "minecraft:cod": {
      "idle_time": 30,
      "top_speed": 5,
      "up_acceleration": 0.3,
      "down_acceleration": 0.3,
      "avg_distance": 40,
      "move_variation": 20
    }
  },
  "defaultBehavior": {
    "idle_time": 25,
    "top_speed": 8,
    "up_acceleration": 0.4,
    "down_acceleration": 0.8,
    "avg_distance": 50,
    "move_variation": 35
  }
}
```

## 🐛 Compatibility

- **Minecraft**: 1.21.1
- **Fabric Loader**: 0.17.2+
- **Fabric API**: 0.116.5+1.21.1
- **Environment**: Client & Server

## 📝 License

This project is licensed under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

_Originally ported to fabric by [kltyton](https://github.com/kltyton) • Updated for 1.21.1_
