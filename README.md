# ⚔️ StrafeDeaths

![Java](https://img.shields.io/badge/Java-8-orange?style=flat-square) ![Spigot](https://img.shields.io/badge/Spigot-1.8.8-yellow?style=flat-square) ![Status](https://img.shields.io/badge/Status-Stable-brightgreen?style=flat-square) ![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

**StrafeDeaths** is a lightweight and efficient death management plugin. Designed for **Spigot 1.8.8**, it is fully compatible with modern versions (up to 1.20+) without requiring NMS or complex packet manipulation.

Developed for **[strafeland.club](https://strafeland.club)**.

---

## 🚀 Key Features

* **⚡ Zero Lag:** Event-based handling ensures minimal impact on server performance.
* **🌩️ Safe Thunder:** Adds a dramatic lightning effect upon death that is purely visual/auditory. It **never** destroys dropped items or causes fire damage.
* **📢 Smart Notifications:** Automatically detects the cause of death and broadcasts a custom message based on the context:
  * **PvP:** Player vs Player.
  * **PvE:** Player vs Mob.
  * **Environmental:** Void or Fall damage.
  * **General:** Any other cause (Lava, Magic, etc.).
* **🌍 Per-World Control:** Easily disable death notifications in specific worlds (e.g., Lobby, Hub) via commands or configuration.
* **🎨 Fully Customizable:** All messages and prefixes are 100% configurable in `messages.yml`.

---

## 🛠️ Commands

The main command is `/death` (alias `/deaths`).

| Command | Description |
| :--- | :--- |
| `/death help` | Shows the help menu with all available commands. |
| `/death notifications [world]` | Toggles death messages for a specific world (or current world). |
| `/death thunder` | Toggles the lightning effect on player death globally. |
| `/death reload` | Reloads `config.yml` and `messages.yml` without restarting. |

---

## ⚙️ Configuration

### `config.yml`
Simple control over mechanics.

```yaml
settings:
  thunder-enabled: true
  disabled-worlds:
    - lobby
    - spawn
```

### `messages.yml`
Supports color codes (&) and placeholders like %victim%, %killer%, and %mob%.

```yaml
chat-messages:
  prefix: "&8[&cStrafeDeaths&8] "
  
  death-player: "&c%victim% &7was slain by &c%killer%&7."
  death-void-fall: "&c%victim% &7fell to their death."
  death-mob: "&c%victim% &7was killed by a &e%mob%&7."
  death-general: "&c%victim% &7died."
```

## 🔧 Installation

1. Download the `.jar` file from the **Releases** section.
2. Place it in your server's `/plugins/` folder.
3. Restart the server.
4. Done! Configure the messages to your liking in `plugins/StrafeDeaths/messages.yml`.

---

## 👨‍💻 Author

Created by zNotHydr0 :)
