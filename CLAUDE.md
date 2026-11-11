# QR Generator — notes for Claude

Android app (Kotlin, Jetpack Compose) that makes plain and styled QR codes. Package `com.sananhaji.qrgenerator`.

- Read `docs/HANDOFF.md` first: current status, release process, secrets, and the plan.
- Marketing context: `.agents/product-marketing.md`; ASO audit and listing texts: `docs/marketing/`.
- Build with JDK 21, not Android Studio's bundled Java 25.
- Flavors: `store` (Play) and `hhm` (company build, toggled by `qrgenerator.hhm.enabled` in `gradle.properties`).
- The repo is public: never commit `signing/` or any key, password or token.
- Check before finishing a code change: `./gradlew :app:assembleStoreDebug :app:testStoreDebugUnitTest`.
