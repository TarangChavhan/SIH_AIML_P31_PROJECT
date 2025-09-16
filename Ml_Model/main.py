import requests, time, mimetypes

HF_TOKEN = "APIKEY"
# Replace with a supported captioning model
API_URL = "https://api-inference.huggingface.co/models/Salesforce/blip-image-captioning-large"

def summarize_local_image(image_path: str):
    mime_type, _ = mimetypes.guess_type(image_path)
    headers = {
        "Authorization": f"Bearer {HF_TOKEN}",
        "Content-Type": mime_type or "image/jpeg"
    }

    with open(image_path, "rb") as f:
        data = f.read()

    for attempt in range(3):
        response = requests.post(API_URL, headers=headers, data=data)
        if response.status_code == 200:
            result = response.json()
            if isinstance(result, list) and "generated_text" in result[0]:
                return result[0]["generated_text"]
            return result
        elif response.status_code == 503:
            print("⏳ Model loading, wait 10s...")
            time.sleep(10)
        elif response.status_code == 404:
            print("❌ Model not found. Perhaps the model is not enabled for inference.")
            return None
        else:
            print("Error:", response.status_code, response.text)
            response.raise_for_status()
    return "Error: Failed to get summary."

if __name__ == "__main__":
    img_path = r"C:\Users\naman\OneDrive\Documents\python\Image_processing_model\School.jpg"
    summary = summarize_local_image(img_path)
    print("Image Summary:", summary)
