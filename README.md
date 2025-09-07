# 🌐 Commons Web Library

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tiglate_commons-web&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tiglate_commons-web)

Tiny helper library for classic Spring MVC apps. It started as a module inside another project and is now a standalone package you can drop into any web app. It offers small, focused utilities so you don't have to re‑write the same paging, sorting, and flash‑message code again.

## ✨ What you get
- Pagination helpers: build page models and steps for your UI (Thymeleaf friendly). See `PaginationUtils` and `PaginationModel`.
- Sorting helpers: parse and build Spring Data `Sort` from query params. See `SortUtils`.
- Flash messages: simple success/error/info messages via session. See `FlashMessages`.
- Globalization bits: formatting helpers and i18n utilities. See `GlobalizationUtils`.
- Web utils and collectors: small helpers for common controller/view tasks. See `WebUtils` and `CustomCollectors`.

## 📦 Add as a dependency
You can use it from GitHub Packages, JitPack, or locally.

### Option A) GitHub Packages (recommended if you already use GitHub Packages)
Add the GitHub Packages repository and the dependency. Authentication is required for GitHub Packages (a GitHub Personal Access Token with read:packages).

```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/tiglate/commons-web</url>
  </repository>
</repositories>

<dependency>
  <groupId>ludo.mentis.aciem</groupId>
  <artifactId>commons-web</artifactId>
  <version>2.0.0</version>
</dependency>
```

In your ~/.m2/settings.xml (or CI env vars), configure credentials:

```xml
<servers>
  <server>
    <id>github</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_GITHUB_TOKEN</password>
  </server>
</servers>
```

### Option B) JitPack
Add the JitPack repository and reference the Git tag or commit you want.

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.tiglate</groupId>
  <artifactId>commons-web</artifactId>
  <version>v2.0.0</version> <!-- or a commit hash -->
</dependency>
```

### Option C) Local install
Clone the repo and run:

```bash
mvn clean install
```

Then depend on it normally:

```xml
<dependency>
  <groupId>ludo.mentis.aciem</groupId>
  <artifactId>commons-web</artifactId>
  <version>2.0.0</version>
</dependency>
```

## ⚙️ Spring configuration
There are components annotated with `@Component`/`@Service` in `ludo.mentis.aciem.commons.web`. Make sure this package is included in component scanning. With Spring Boot it happens automatically if your main class is a parent package; otherwise, explicitly scan:

```java
@Configuration
@ComponentScan({
  "your.base.package",
  "ludo.mentis.aciem.commons.web"
})
public class AppConfig { }
```

## 🧪 Quick examples
Just a few light snippets to show the flavor.

- Pagination (build model for UI):
```java
var model = paginationUtils.buildModel(totalItems, page, size);
// model.steps() -> list of PaginationStep for rendering
```

- Sorting from request params:
```java
Sort sort = sortUtils.parseSort("name,asc;createdAt,desc");
```

- Flash messages:
```java
FlashMessages.success(redirectAttributes, "User created successfully");
FlashMessages.error(redirectAttributes, "Oops, something went wrong");
```

- Globalization helpers:
```java
String formatted = globalizationUtils.formatCurrency(new BigDecimal("12.34"), Locale.US);
```

- Web utils:
```java
String baseUrl = WebUtils.baseUrl(request);
```

## 📄 Thymeleaf fragments & templates
This library expects some common fragments (pagination controls, alerts). Sharing templates from JARs can be clunky, so the pragmatic approach is: peek at a project that uses this lib and copy the fragments you like into your app. Keeps things simple and transparent.

## 🙌 Contributing
Issues and PRs are welcome. Keep the scope tight and the API small.