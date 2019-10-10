# libermo

## Getting Started

### Get credentials.json

https://console.firebase.google.com/u/0/project/{PROJECT_ID}/settings/serviceaccounts/adminsdk

### Development

```bash
cp /path/to/credentials.json src/main/resources/credentials.json
./gradlew bootRun
```

### Production

```bash
cp /path/to/credentials.json src/main/resources/credentials.json
./gradlew bootJar
SPRING_PROFILES_ACTIVE=prod java -jar build/libs/*.jar
```