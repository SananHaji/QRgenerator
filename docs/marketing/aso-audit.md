# ASO audit — QR Generator (Google Play)

Date: 2026-11-08 · Listing: https://play.google.com/store/apps/details?id=com.sananhaji.qrgenerator
Tier: **Challenger** (new app, 0+ downloads, no ratings). Scored strictly against textbook ASO.
Method: `marketing-skills:aso` scoring rubric; context in `.agents/product-marketing.md`.

## Score card (before)

| Dimension | Weight | Score | Why |
| --- | --- | --- | --- |
| Title | 20% | 5/10 | "QR Generator – Custom QR Code" repeats "QR"; misses "maker", "logo", "color". |
| Description | 15% | 6/10 | Good structure, but "logo", "QR code maker", use cases (Instagram, WhatsApp, menu) barely appear. |
| Visual assets | 25% | 4/10 | 5 raw UI screenshots, no captions; first shot is the styled QR (good). Feature graphic present, no video. |
| Ratings & reviews | 20% | 1/10 | No ratings; no in-app review prompt. |
| Metadata & freshness | 10% | 6/10 | Updated Nov 2026, Tools category; only 2 localizations (en, az). |
| Conversion signals | 10% | 6/10 | Free, no IAP, "no ads" claim; no social proof yet. |
| **Total** | | **44/100 (D)** | |

## Market notes

- "qr code generator" is crowded: most top results are "Scanner & Generator" combos with 4.4–4.9 ratings.
- Winnable long-tail terms: "qr code with logo", "custom qr code", "qr code maker", "colorful qr code", "qr code for instagram / whatsapp / menu", "qr kod yaratmaq" (az), "qr kod oluşturucu" (tr), "генератор qr кодов" (ru).
- Exact search volumes need paid tools (AppTweak, Sensor Tower); not assessed.

## Changes prepared

1. **Title, short and full description** rewritten in 6 languages — `docs/marketing/listing/*.txt` (en-US, az-AZ, tr-TR, ru-RU, es-419, pt-BR). All within 30 / 80 / 4000 limits.
2. **Captioned screenshots** (1080 × 1920) in English and Azerbaijani — `docs/marketing/store/{en,az}-1..5.png`.
3. **In-app review prompt** (version 1.2): the Play rating sheet is requested once, after the user's 2nd successful save (`review/ReviewEligibility.kt`, `review/InAppReviewLauncher.kt`).
4. **Landing page for SEO/GEO** — `docs/marketing/site/` (index.html with MobileApplication + FAQPage JSON-LD, sitemap.xml, llms.txt) for GitHub Pages.

## Expected score after changes

Title 9, Description 8, Visual 6, Ratings 3 (rises as reviews come in), Metadata 8, Conversion 6 → about **67/100 (C)**.

## Next steps (by impact)

1. **Add a QR scanner** — most top competitors are "Scanner & Generator"; scanning searches are far larger than generating. Title could then become "QR Code Generator & Scanner".
2. **More QR types**: WiFi, contact (vCard), email, SMS, phone, location — common search terms ("wifi qr code").
3. **Translate the app UI** into tr, ru, es, pt so the localized listings match the app.
4. **Preview video** (15–30 s screen recording: paste link → add logo → save).
5. **First 10 ratings**: ask friends who installed the app to rate it; answer every review.
6. **Store listing experiment** in Play Console: test the screenshot set or short description after ~1 week of traffic.
7. **Submit the landing page** to Google Search Console and share it on Reddit (r/androidapps), Product Hunt and AlternativeTo.
