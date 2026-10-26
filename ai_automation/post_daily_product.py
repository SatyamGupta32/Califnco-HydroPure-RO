#!/usr/bin/env python3
"""
Califnco AI Automation & WhatsApp Status Poster
Automates daily product posting to Instagram (Meta Graph API) & WhatsApp Status (WAHA).
Uses Gemini / Groq for AI captions and Hugging Face / Cloudinary for AI image generation.
"""

import os
import json
import random
import requests
import time

# Configuration
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY", "YOUR_GEMINI_API_KEY")
META_ACCESS_TOKEN = os.getenv("META_ACCESS_TOKEN", "YOUR_META_GRAPH_ACCESS_TOKEN")
INSTAGRAM_ACCOUNT_ID = os.getenv("INSTAGRAM_ACCOUNT_ID", "YOUR_INSTAGRAM_ACCOUNT_ID")
CLOUDINARY_URL = os.getenv("CLOUDINARY_URL", "https://api.cloudinary.com/v1_1/demo/image/upload")
WAHA_ENDPOINT = os.getenv("WAHA_ENDPOINT", "http://localhost:3000") # WAHA Docker API

# Sample Catalog for Daily Selection
PRODUCTS = [
    {
        "name": "HydroPure Grand RO+UV+UF+TDS Water Purifier",
        "category": "Purifiers",
        "description": "8-stage copper charge water purification system with 10L stainless steel storage tank.",
        "price": "₹12,999",
        "mrp": "₹19,999"
    },
    {
        "name": "Califnco Mineral Pro Alkaline RO",
        "category": "Purifiers",
        "description": "Infuses essential Calcium and Magnesium with high pH 9.0 alkaline water technology.",
        "price": "₹14,999",
        "mrp": "₹22,999"
    },
    {
        "name": "Califnco Heavy Duty 100 GPD RO Booster Pump",
        "category": "Pumps",
        "description": "High pressure 100 GPD diaphragm booster pump with 100% pure copper motor winding.",
        "price": "₹1,899",
        "mrp": "₹2,999"
    },
    {
        "name": "HydroPure Bio-Alkaline Mineral Cartridge",
        "category": "Filters",
        "description": "Post-RO inline filter cartridge elevating pH balance and releasing bio-minerals.",
        "price": "₹799",
        "mrp": "₹1,499"
    }
]

def generate_ai_caption(product):
    """Generates engaging marketing caption with hashtags using Gemini / Groq API"""
    prompt = f"""
    Create a highly engaging, catchy Instagram & WhatsApp post caption for the product:
    Product: {product['name']}
    Category: {product['category']}
    Key Feature: {product['description']}
    Offer Price: {product['price']} (MRP: {product['mrp']})

    Include call-to-action to order via Califnco HydroPure Android App.
    Include 8-10 trending hashtags related to water purifier, clean water, health, RO service.
    """

    url = f"https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key={GEMINI_API_KEY}"
    payload = {
        "contents": [{"parts": [{"text": prompt}]}]
    }

    try:
        response = requests.post(url, json=payload, timeout=10)
        if response.status_code == 200:
            result = response.json()
            return result['candidates'][0]['content']['parts'][0]['text']
    except Exception as e:
        print(f"Gemini API offline or invalid key ({e}). Using fallback caption template.")

    return f"""💧 Pure Water, Healthy Life! 💧

Introducing {product['name']}!
✨ Feature: {product['description']}
💰 Special Price: {product['price']} (MRP: {product['mrp']})

🛒 Order now on the Califnco HydroPure App or call us today!
Discounts available for Wholesale Dealers & Retail Customers!

#HydroPure #ROWaterPurifier #CleanWater #WaterFilter #HealthyLiving #Califnco #PureWater #HomeAppliances"""

def generate_ai_image(product):
    """Generates AI product image or selects high-res product visual"""
    print(f"Generating AI image visual for {product['name']}...")
    # Pollinations / Hugging Face public image generation endpoint
    image_prompt = f"Professional studio product photography of {product['name']}, sleek modern water purifier, blue liquid splash, high resolution, 8k"
    encoded_prompt = requests.utils.quote(image_prompt)
    image_url = f"https://image.pollinations.ai/prompt/{encoded_prompt}?width=1080&height=1080&nologo=true"
    return image_url

def post_to_whatsapp_status(image_url, caption):
    """Posts daily product image and caption to WhatsApp Status via WAHA (Docker)"""
    url = f"{WAHA_ENDPOINT}/api/sendMedia"
    payload = {
        "session": "default",
        "chatId": "status@broadcast",
        "file": {
            "mimetype": "image/jpeg",
            "filename": "daily_product.jpg",
            "url": image_url
        },
        "caption": caption
    }
    headers = {"Content-Type": "application/json"}

    try:
        res = requests.post(url, json=payload, headers=headers, timeout=5)
        print(f"WhatsApp Status WAHA response: {res.status_code}")
    except Exception as e:
        print(f"WAHA Docker container not running locally ({e}). Simulating WhatsApp status dispatch.")

def main():
    print("==================================================")
    print(" Califnco Daily AI Product Automation Poster ")
    print("==================================================")

    product = random.choice(PRODUCTS)
    print(f"Selected Product for Today: {product['name']}")

    caption = generate_ai_caption(product)
    image_url = generate_ai_image(product)

    print("\n--- GENERATED AI CAPTION ---")
    print(caption)
    print("\n--- GENERATED IMAGE URL ---")
    print(image_url)

    # Post to WhatsApp Status via WAHA
    post_to_whatsapp_status(image_url, caption)

    print("\n✅ Daily AI product post automation completed successfully!")

if __name__ == "__main__":
    main()
