# Muricize agent guide

Muricize is a single-module Android app. The interesting logic is a pure-Java name ranker. The screens around it are three activities. Read this file before changing either.

`CLAUDE.md` points here. Do not copy this guide into another rules file.

## Architecture

`SplashScreenActivity` waits three seconds and starts `NameFinder`. `NameFinder` sends one string extra, `normalizedFirstName + "," + genderTag`, to `DisplayResult`. The gender tag is `Male` or `Female` and is also the asset file name.

`Name`, `PossibleNames`, and `Utilities` do not import Android. `QuoteBank` is the only class that reads assets. Unit tests cover the Java side by reading `src/main/assets` from disk. They do not boot an emulator.

Keep that split. A change to scoring belongs in `Utilities.vowelMatcher` and `Name.getAmerican`, with a test that fails if the ranked list moves.

## How a name is scored

This is the 2017 heuristic. Preserve it.

- `Name.normalize` keeps the first whitespace-delimited token and title-cases it with `Locale.US`.
- Candidates with a different first letter are dropped. Blank lines are skipped.
- Score starts at 1 for the shared initial.
- `Utilities.getVowels` keeps lowercase `a e i o u` only. The capital first letter is not a vowel, so `Amy` ties every A name at score 1.
- `vowelMatcher` scans the candidate's vowels from the start for every input vowel. Its counter `j` is not reset between input vowels. The position bonus applies only when `i == j` at the moment of a match.
- `Collections.sort` is stable. Equal scores stay in asset order, which is 2011 popularity order.
- The visible row is `Name  (Score= N)` with two spaces before the parenthesis. `PossibleNames.toString` owns that format.

`assets/Male` and `assets/Female` are the Social Security top 1,000 for 2011. Do not sort, reformat, or "update" them. Tie order is part of the result.

## Conventions

- Java 17 source. AndroidX only. No Kotlin unless the owner asks.
- User-visible copy goes in `strings.xml`. The gender asset key stays the radio `android:tag`, not the label, so a wording change does not break file loading.
- Formatting is Spotless with google-java-format 1.36.1 in AOSP mode (4 spaces). Run `./gradlew spotlessApply` before committing Java.
- The module directory is `Muric-ize`. Leave the hyphen.
- Application id stays `com.unit401.pranav.muricize`. Version stays `1.0` / versionCode `1` unless the owner is shipping a new build.
- Do not add a license file.
- Do not commit `local.properties`, API keys, keystores, or built APKs. There are no credentials in this project.

## Commands

JDK 21. `ANDROID_HOME` must point at an SDK with platform 37.

```bash
./gradlew test                 # matcher tests
./gradlew lintDebug            # Android lint
./gradlew spotlessCheck        # format check, also run in CI
./gradlew spotlessApply        # fix format
./gradlew assembleDebug        # Muric-ize/build/outputs/apk/debug/Muric-ize-debug.apk
```

CI (`.github/workflows/ci.yml`) runs `test lintDebug spotlessCheck assembleDebug` on JDK 21.

`./gradlew test` is the supported way to run tests. The asset tests use module-relative paths (`src/main/assets`), which is Gradle's unit-test working directory.

## Gotchas

- Empty input used to crash on `charAt(0)`. The form now refuses it, and `DisplayResult` still guards a missing extra. Keep both.
- The splash used to `sleep` on a background thread and could start `NameFinder` after the activity was gone. It is a main-thread `Handler` now. `onDestroy` cancels the callback, so pressing back during the splash does not open the form. A rotation starts the three seconds over.
- `QuoteBank.readLine` returns an empty list if the asset is missing. It does not throw. The path argument is the gender tag.
- minSdk is 23 because AppCompat 1.8.0 dropped older releases. The 2017 manifest said 19.
- Gradle is pinned to 9.6.0 in `gradle/wrapper/gradle-wrapper.properties`, including a distribution checksum. That is the Gradle version AGP 9.4.1 requires. Do not bump Gradle just because lint notices a newer one. The ignore is in `Muric-ize/lint.xml`.
- `lint.xml` also ignores the custom-splash, full-bleed launcher icon, and splash overdraw findings. Those are the original art, not unfinished work.
- Launcher icons are the 2017 full-bleed PNGs. Do not replace them with an adaptive icon as a drive-by.
- The eagle JPEG stays in `res/drawable/` (no density qualifier). Moving it to `drawable-nodpi` changes how large it draws. Leave it unless you are deliberately redoing the splash.
- `styles.xml` and the template `colorPrimary` values were unused and have been removed. The manifest theme is `Theme.AppCompat.DayNight.NoActionBar`.
- There is no instrumented test suite. The old `ApplicationTestCase` class does not exist on current Android and was removed.

## What not to "improve"

Do not replace the vowel heuristic with edit distance, Soundex, or a newer baby-name list. Do not rename `QuoteBank`. Do not retitle the button. The app's personality is the eagle, the **'Muric-ize!!** button, and this slightly odd score.
