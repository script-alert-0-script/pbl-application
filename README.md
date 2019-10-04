# libermo

## Development

```bash
cp /path/to/credentials.json src/main/resources/credentials.json
./gradlew bootRun
```

## Production

```bash
cp /path/to/credentials.json src/main/resources/credentials.json
./gradlew bootJar
SPRING_PROFILES_ACTIVE=prod java -jar build/libs/*.jar
```