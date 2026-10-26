# Califnco AI Automation & WhatsApp Status Poster

This module automates posting daily water purifier products to Instagram (Meta Graph API) and WhatsApp Status (via WAHA open-source API in Docker) using Gemini API for AI captions and Pollinations/HuggingFace for AI product images.

## Features
- **Daily Selection**: Selects products automatically from catalog / Google Sheets.
- **AI Captions**: Uses Google Gemini 1.5 Flash API or Groq to generate compelling marketing captions with relevant hashtags (`#ROWaterPurifier #CleanWater #HydroPure`).
- **AI Product Photography**: Generates 1080x1080 high-res product renders using Pollinations / Hugging Face.
- **WhatsApp Status Automation (WAHA)**: Posts directly to WhatsApp Status (`status@broadcast`) using local WAHA Docker container.
- **Instagram Automation**: Integrates with Meta Graph API for automated feed posting.

## How to Run

1. **Start Local Docker Services (WAHA & n8n)**:
   ```bash
   docker-compose up -d
   ```
   This starts:
   - WAHA API server at `http://localhost:3000`
   - n8n Workflow Automation at `http://localhost:5678`

2. **Execute Python Daily Automation Script**:
   ```bash
   python post_daily_product.py
   ```

3. **Environment Variables (Optional)**:
   - `GEMINI_API_KEY`: Your Google Gemini API Key
   - `META_ACCESS_TOKEN`: Meta Graph API Access Token for Instagram
   - `WAHA_ENDPOINT`: WAHA WhatsApp API base URL (`http://localhost:3000`)
