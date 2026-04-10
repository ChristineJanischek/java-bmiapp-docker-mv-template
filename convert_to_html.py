#!/usr/bin/env python3
"""
Konvertiert Markdown-Dateien in formatierte HTML-Dateien mit Syntax-Highlighting.
Nutzt Highlight.js von CDN für Code-Highlighting.
"""

import os
import re
import sys
from pathlib import Path

def extract_title_from_markdown(content):
    """Extrahiert den Titel aus der Markdown-Datei (erste H1)."""
    match = re.search(r'^# (.+)$', content, re.MULTILINE)
    if match:
        return match.group(1).strip()
    return "Anleitung"

def markdown_to_html_basic(content):
    """
    Konvertiert einfaches Markdown zu HTML.
    Unterstützt:
    - Überschriften (# ## ###)
    - Fett und Kursiv (* **)
    - Codeblöcke (```lang ... ```)
    - Inline-Code (`...`)
    - Zitate (> ...)
    - Listen (- ...)
    - Horizontale Linien (---)
    """
    lines = content.split('\n')
    html_lines = []
    in_code_block = False
    code_lang = ''
    code_buffer = []
    
    i = 0
    while i < len(lines):
        line = lines[i]
        
        # Code-Blöcke
        if line.strip().startswith('```'):
            if not in_code_block:
                in_code_block = True
                code_lang = line.strip()[3:].strip() or 'plaintext'
            else:
                # Code-Block beenden
                code_content = '\n'.join(code_buffer)
                # HTML-Entities escapen
                code_content = (code_content.replace('&', '&amp;')
                                           .replace('<', '&lt;')
                                           .replace('>', '&gt;')
                                           .replace('"', '&quot;'))
                html_lines.append(f'<pre><code class="language-{code_lang} hljs">{code_content}</code></pre>')
                in_code_block = False
                code_lang = ''
                code_buffer = []
            i += 1
            continue
        
        if in_code_block:
            code_buffer.append(line)
            i += 1
            continue
        
        line = line.rstrip()
        
        # Horizontale Linien
        if line.strip() in ['---', '***', '___']:
            html_lines.append('<hr />')
            i += 1
            continue
        
        # Überschriften
        if line.startswith('##### '):
            text = markdown_inline_to_html(line[6:].strip())
            html_lines.append(f'<h5>{text}</h5>')
            i += 1
            continue
        elif line.startswith('#### '):
            text = markdown_inline_to_html(line[5:].strip())
            html_lines.append(f'<h4>{text}</h4>')
            i += 1
            continue
        elif line.startswith('### '):
            text = markdown_inline_to_html(line[4:].strip())
            html_lines.append(f'<h3>{text}</h3>')
            i += 1
            continue
        elif line.startswith('## '):
            text = markdown_inline_to_html(line[3:].strip())
            html_lines.append(f'<h2>{text}</h2>')
            i += 1
            continue
        elif line.startswith('# '):
            text = markdown_inline_to_html(line[2:].strip())
            html_lines.append(f'<h1>{text}</h1>')
            i += 1
            continue
        
        # Blockquotes
        if line.startswith('> '):
            quote_lines = [line[2:].strip()]
            j = i + 1
            while j < len(lines) and lines[j].startswith('> '):
                quote_lines.append(lines[j][2:].strip())
                j += 1
            quote_text = markdown_inline_to_html(' '.join(quote_lines))
            html_lines.append(f'<blockquote>{quote_text}</blockquote>')
            i = j
            continue
        
        # Listen
        if line.startswith('- '):
            # Längere Liste sammeln
            list_items = []
            j = i
            while j < len(lines) and lines[j].startswith('- '):
                item_text = markdown_inline_to_html(lines[j][2:].strip())
                list_items.append(f'<li>{item_text}</li>')
                j += 1
            html_lines.append('<ul>')
            html_lines.extend(list_items)
            html_lines.append('</ul>')
            i = j
            continue
        
        # Leere Zeilen
        if not line.strip():
            html_lines.append('<p></p>')
            i += 1
            continue
        
        # Normale Absätze
        text = markdown_inline_to_html(line.strip())
        html_lines.append(f'<p>{text}</p>')
        i += 1
    
    return '\n'.join(html_lines)

def markdown_inline_to_html(text):
    """Konvertiert Inline-Markdown-Elemente zu HTML."""
    # Code (muss vor Bold/Italic sein!)
    text = re.sub(r'`([^`]+)`', r'<code>\1</code>', text)
    
    # Links
    text = re.sub(r'\[([^\]]+)\]\(([^)]+)\)', r'<a href="\2">\1</a>', text)
    
    # Fett und Kursiv
    text = re.sub(r'\*\*([^*]+)\*\*', r'<strong>\1</strong>', text)
    text = re.sub(r'__([^_]+)__', r'<strong>\1</strong>', text)
    text = re.sub(r'\*([^*]+)\*', r'<em>\1</em>', text)
    text = re.sub(r'_([^_]+)_', r'<em>\1</em>', text)
    
    # HTML-Entities escapen (aber nicht in Tags)
    # Das ist kompliziert - nur für Text außerhalb von Tags
    parts = re.split(r'(<[^>]+>)', text)
    for i in range(0, len(parts), 2):  # Nur ungerade Indizes (Text, nicht Tags)
        if i < len(parts) and not parts[i].startswith('<'):
            # parts[i] = (parts[i].replace('&', '&amp;')
            #                     .replace('<', '&lt;')
            #                     .replace('>', '&gt;'))
            pass
    return text

def create_html_document(markdown_content, filename):
    """Erstellt ein komplettes HTML-Dokument aus Markdown."""
    title = extract_title_from_markdown(markdown_content)
    html_body = markdown_to_html_basic(markdown_content)
    
    html_doc = f'''<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{title}</title>
    
    <!-- Highlight.js CDN für Syntax-Highlighting -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/atom-one-light.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script>hljs.highlightAll();</script>
    
    <style>
        * {{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }}
        
        body {{
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            max-width: 1000px;
            margin: 40px auto;
            line-height: 1.7;
            color: #333;
            padding: 0 20px;
        }}
        
        h1, h2, h3, h4, h5 {{
            color: #0066cc;
            margin-top: 30px;
            margin-bottom: 15px;
            font-weight: 600;
        }}
        
        h1 {{
            font-size: 2.5em;
            border-bottom: 3px solid #0066cc;
            padding-bottom: 10px;
            margin-bottom: 25px;
        }}
        
        h2 {{
            font-size: 1.8em;
            margin-top: 40px;
        }}
        
        h3 {{
            font-size: 1.4em;
            margin-top: 25px;
        }}
        
        h4 {{
            font-size: 1.2em;
        }}
        
        h5 {{
            font-size: 1.1em;
        }}
        
        p {{
            margin-bottom: 15px;
            text-align: justify;
        }}
        
        code {{
            background-color: #f5f5f5;
            padding: 2px 6px;
            border-radius: 3px;
            font-family: 'Courier New', 'Consolas', monospace;
            font-size: 0.95em;
        }}
        
        pre {{
            background-color: #f5f5f5;
            padding: 16px;
            border-radius: 6px;
            border-left: 4px solid #0066cc;
            overflow-x: auto;
            margin-bottom: 20px;
            font-size: 0.9em;
            line-height: 1.5;
        }}
        
        pre code {{
            background-color: transparent;
            padding: 0;
            border-radius: 0;
            font-size: 1em;
        }}
        
        blockquote {{
            border-left: 4px solid #0066cc;
            margin: 20px 0;
            padding-left: 16px;
            font-style: italic;
            color: #666;
            background-color: #f0f7ff;
            padding: 12px 16px;
            border-radius: 4px;
        }}
        
        ul, ol {{
            margin: 15px 0 15px 30px;
            padding: 0;
        }}
        
        li {{
            margin-bottom: 8px;
        }}
        
        table {{
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }}
        
        th, td {{
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }}
        
        th {{
            background-color: #0066cc;
            color: white;
            font-weight: 600;
        }}
        
        tr:nth-child(even) {{
            background-color: #f9f9f9;
        }}
        
        a {{
            color: #0066cc;
            text-decoration: none;
            transition: color 0.2s;
        }}
        
        a:hover {{
            color: #004d99;
            text-decoration: underline;
        }}
        
        hr {{
            border: none;
            border-top: 2px solid #0066cc;
            margin: 30px 0;
        }}
        
        /* Syntax Highlighting Anpassungen */
        .hljs {{
            background-color: transparent;
            padding: 0;
        }}
        
        .hljs-string {{ color: #2d8659; }}
        .hljs-number {{ color: #2d8659; }}
        .hljs-literal {{ color: #d9534f; }}
        .hljs-attr {{ color: #d9534f; }}
        .hljs-built_in {{ color: #0066cc; }}
        .hljs-type {{ color: #0066cc; }}
        .hljs-class {{ color: #0066cc; font-weight: bold; }}
        .hljs-keyword {{ color: #d9534f; font-weight: bold; }}
        .hljs-function {{ color: #0066cc; }}
        .hljs-title {{ color: #0066cc; }}
        .hljs-comment {{ color: #999; font-style: italic; }}
        
        @media print {{
            body {{
                margin: 0;
            }}
            h1, h2 {{
                page-break-after: avoid;
            }}
            pre {{
                page-break-inside: avoid;
            }}
        }}
        
        @media (max-width: 768px) {{
            body {{
                margin: 20px auto;
                padding: 0 10px;
            }}
            h1 {{
                font-size: 2em;
            }}
            h2 {{
                font-size: 1.5em;
            }}
            h3 {{
                font-size: 1.2em;
            }}
            pre {{
                font-size: 0.8em;
                padding: 12px;
            }}
        }}
    </style>
</head>
<body>
{html_body}
</body>
</html>'''
    
    return html_doc

def convert_markdown_to_html(markdown_file, output_file=None):
    """Konvertiert eine Markdown-Datei zu HTML."""
    if output_file is None:
        output_file = markdown_file.replace('.md', '.html')
    
    # Markdown-Inhalt lesen
    with open(markdown_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # HTML generieren
    html_doc = create_html_document(content, os.path.basename(markdown_file))
    
    # Speichern
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(html_doc)
    
    print(f"✓ Konvertiert: {markdown_file} → {output_file}")
    return output_file

def main():
    """Konvertiert alle Anleitung_VERSION_*.md Dateien zu HTML."""
    anleitungen_dir = Path('/workspaces/java-bmiapp-docker-mv-template/docs/ANLEITUNGEN')
    
    if not anleitungen_dir.exists():
        print(f"Fehler: Verzeichnis {anleitungen_dir} nicht gefunden!")
        sys.exit(1)
    
    # Alle ANLEITUNG_VERSION_*.md Dateien finden
    md_files = sorted(anleitungen_dir.glob('ANLEITUNG_VERSION_*.md'))
    
    if not md_files:
        print(f"Keine ANLEITUNG_VERSION_*.md Dateien in {anleitungen_dir} gefunden!")
        sys.exit(1)
    
    print(f"Konvertiere {len(md_files)} Anleitungen zu HTML mit Syntax-Highlighting...\n")
    
    converted_files = []
    for md_file in md_files:
        html_file = md_file.with_suffix('.html')
        try:
            convert_markdown_to_html(str(md_file), str(html_file))
            converted_files.append((md_file.name, html_file.name))
        except Exception as e:
            print(f"✗ Fehler bei {md_file.name}: {e}")
    
    print(f"\n{'='*60}")
    print(f"Erfolgreich konvertiert: {len(converted_files)}/{len(md_files)} Dateien\n")
    
    for md_name, html_name in converted_files:
        print(f"  • {md_name:40s} → {html_name}")
    
    print(f"\nDie HTML-Dateien befinden sich in: {anleitungen_dir}")
    return len(converted_files) == len(md_files)

if __name__ == '__main__':
    success = main()
    sys.exit(0 if success else 1)
