# AlertTermExtraction

Matches query terms against alert content using the Prewave test API.

## How it works

1. Fetches query terms once from `testQueryTerm`
2. Fetches alerts N times from `testAlerts` (default: 100 — the endpoint returns different data each call)
3. For each alert, checks whether any query term occurs in a content field of the same language
4. Emits unique `alertId,termId` pairs — no duplicates across iterations

**Matching rules** (per `keepOrder` flag):
- `keepOrder=true` — all words of the term must appear consecutively in that order
- `keepOrder=false` — all words must appear somewhere in the text, in any order

## Run

```bash
mvn package -DskipTests
java -DapiKey=<your-key> -jar target/AlertTermExtraction-1.0-SNAPSHOT.jar
```

Or pass the key as an environment variable:

```bash
API_KEY=<your-key> java -jar target/AlertTermExtraction-1.0-SNAPSHOT.jar
```

Override iteration count:

```bash
java -DapiKey=<your-key> -Diterations=50 -jar target/...jar
```

## Test

Unit tests (no API key needed):

```bash
mvn test
```

Integration test against the real API:

```bash
mvn test -Dtest=ExtractionIntegrationTest -DapiKey=<your-key>
```
