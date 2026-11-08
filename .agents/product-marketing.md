# Product Marketing Context

Last updated: 2026-11-08

## Product Overview

- **Name:** QR Generator – Custom QR Code (package `com.sananhaji.qrgenerator`)
- **One-liner:** An Android app that turns any text or link into a QR code, plain or fully styled with your colors, shapes, logo and a background picture.
- **Category:** Google Play → Tools. Free, no in-app purchases.
- **Platform:** Android only (minSdk 24, targetSdk 36). Store listing in English and Azerbaijani; app UI in English and Azerbaijani.
- **Core features:**
  - Simple mode: type text or a URL, get a black-and-white code, save or share.
  - Custom mode: separate colors for pixels, eye frames, eye centers and background (transparent supported); pixel shapes (square, rounded, circle, diamond, star, lines); eye shapes; center logo with size and shape; background picture with readable-eye highlighting; margin.
  - Saves 1024 × 1024 PNG to Pictures/QRCodeGenerator; share sheet.
  - Unicode text (Azerbaijani, Cyrillic, Arabic and others) encoded as UTF-8.
- **Not supported yet:** QR scanning, WiFi / contact (vCard) / email / SMS / phone / location QR types, SVG or PDF export, history of created codes, batch generation (only in the private HHM build).
- **Business model:** none yet; no ads, no account.

## Target Audience

- Small business owners, cafés, shops and freelancers who want a branded QR for a menu, Instagram, WhatsApp or website link, printed on flyers, cards or tables.
- Event organizers, teachers and students making codes for slides, posters and invitations.
- Everyday users who need a quick code for a link or text and dislike ad-heavy apps.
- Primary markets: Azerbaijan first (developer's home market, Azerbaijani UI), then global English-speaking users.

## Problems & Pain Points

- Most QR generator apps are full of ads, push subscriptions or watermark styled codes.
- "Dynamic QR" services lock codes behind paid plans; the code stops working when the subscription ends.
- Making a branded QR (logo + colors) usually needs a website on a desktop.
- Users worry that a styled QR will not scan.

## Competitive Landscape

Search "qr code generator" on Play is crowded: QR Code Generator (YenKu), QRHyper, ME-QR, QRTIGER, Uniqode, QR Designer (Alexander Zhirkevich, same rendering library), Zoho Barcode & QR Generator, many "Scanner & Generator" combos. Most top results combine scanning and generating; most show ads or sell a Pro tier.

## Differentiation

- No ads, no account, no watermark, no subscription. Codes are static and never expire.
- Works offline; text and pictures never leave the device.
- Real design control on the phone: logo, background photo, colors, shapes, with the eyes kept readable.
- High-resolution 1024 px PNG output ready for print.
- Native support for non-Latin text.

## Objections

- "Will a styled code still scan?" → error correction is raised automatically when a logo or background is used; eyes stay high-contrast.
- "Is it really free?" → yes; no ads, no paid tier.
- "Why no scanner?" → generation-focused today; the camera app scans QR codes on modern Android.

## Customer Language

- "QR code maker", "QR code generator with logo", "custom QR code", "QR code for Instagram / WhatsApp / menu", "colorful QR", "QR with picture", "QR code without ads".
- Azerbaijani: "QR kod yaratmaq", "QR kod generator", "loqolu QR kod", "rəngli QR kod".

## Brand Voice

Plain, friendly, practical. Short sentences. No hype words ("best", "#1", "ultimate"). Lead with what the user gets.

## Proof Points

- New app (released Nov 2026); no ratings yet. Developer has other published apps (E-Kurs rated 4.9).
- Rendering based on the open-source custom-qr-generator library, used by QR Designer.

## Goals

- Reach first 100 installs and 10 ratings.
- Rank for long-tail Play searches: "qr code with logo", "custom qr code", "qr code maker no ads", "qr kod yaratmaq".
- Grow organically; no paid ads budget.

## Learnings

- Firebase Analytics (first week): up to 8 daily active users, mostly testers. Play Console shows no acquisitions yet.

## Changelog

- 2026-11-08: Created from the code base, the live listing and Play/Firebase consoles.
