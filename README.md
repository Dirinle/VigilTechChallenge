## What it is
It`s HTTP server that format text with given line limit
## Requirements
- docker
- curl
## How to run
In root folder run:
- docker build . -t {YOUR_TAG}
- docker run -p 0.0.0.0:8080:8080 {YOUR_TAG}
## How to use
- Just POST request with this body structure {"text":"TEXT": "limitPerLine": NUMBER}
- Example: 
<code>curl --location '127.0.0.1:8080/format' \
--header 'Content-Type: application/json' \
--data '{
"text": "Hello longword",
    "limitPerLine": 5
}'</code>