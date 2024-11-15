# Read Me First
Application for notifying developer team about accidents via Telegram Bot

# Getting Started
Set next properties for connecting to Telegram Bot
- BOT_TOKEN
- BOT_CHAT_ID
- BOT_NAME

# Usage
Do HTTP request to send message to Telegram Bot

```curl -X POST -H "Content-Type: text/plain" -d 'message' localhost:1234```