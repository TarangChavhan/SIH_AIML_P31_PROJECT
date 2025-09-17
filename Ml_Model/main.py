import requests, base64

API_KEY = "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"
endpoint = "https://openrouter.ai/api/v1/chat/completions"

def summarize_issue(image_path):
    # Encode image
    with open(image_path, "rb") as f:
        img_bytes = f.read()
    base64_str = base64.b64encode(img_bytes).decode("utf-8")
    data_url = f"data:image/jpeg;base64,{base64_str}"

    # Payload with Indian citizen tone
    payload = {
        "model": "openai/gpt-4o-mini",   # vision-capable
        "messages": [
            {
                "role": "user",
                "content": [
                    {
                        "type": "text",
                        "text": (
                            "You are an assistant helping citizens report public problems to local Indian government authorities. "
                            "Write reports in a simple, natural Indian citizen tone. "
                            "Rules:\n"
                            "1. If the photo shows a public issue (potholes, garbage, broken streetlights, waterlogging, damaged roads, etc.), "
                            "start with 'Report:' and write a short complaint in plain language, 1–2 sentences. "
                            "2. The report should sound like a normal Indian person writing to the municipal office, not very formal. "
                            "Example: 'Report: The road has big potholes filled with water. It is risky for people and vehicles, please repair soon.' "
                            "3. If the photo is irrelevant (documents, MCQs, random objects, selfies), reply exactly: 'Not a public issue photo.'"
                        )
                    },
                    {"type": "image_url", "image_url": {"url": data_url}}
                ]
            }
        ]
    }

    headers = {"Authorization": f"Bearer {API_KEY}", "Content-Type": "application/json"}
    response = requests.post(endpoint, json=payload, headers=headers)
    result = response.json()

    try:
        return result["choices"][0]["message"]["content"]
    except:
        return "Error in processing image."

# Example tests
print(summarize_issue("MCQ.jpg"))       
# -> "Not a public issue photo."

print(summarize_issue("DamageRoad.jpg"))   
# -> "Report: The road has many potholes filled with water. It is very unsafe for people and vehicles, kindly repair it soon."
