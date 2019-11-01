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
./gradlew build -x test
MYSQL_URL='mysql://user:pass@example.com:3306/name' SPRING_PROFILES_ACTIVE=prod java -jar build/libs/*.jar
```