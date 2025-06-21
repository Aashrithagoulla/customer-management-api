#!/bin/bash

# Create a temporary directory with the correct structure
TEMP_DIR="github_upload"
mkdir -p $TEMP_DIR

# Copy only the important files, preserving directory structure
find . -type f \
    \( -name "*.java" -o -name "*.properties" -o -name "*.xml" -o -name "*.yaml" -o -name "Dockerfile" -o -name "README.md" -o -name "run.sh" \) \
    -not -path "*/target/*" \
    -not -path "*/.git/*" \
    | while read file; do
    dest="$TEMP_DIR/$file"
    mkdir -p "$(dirname "$dest")"
    cp "$file" "$dest"
    echo "Copied $file to $dest"
done

echo "Done! All important files copied to $TEMP_DIR folder."
echo "You can now upload the contents of this folder to GitHub."
