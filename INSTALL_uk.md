> **Language / Мова:** [English](INSTALL.md) | [Português](INSTALL_pt_BR.md) | [Български](INSTALL_bg.md) | **Українська**

# Посібник зі встановлення

# dfieldX та pplaneX

Цей посібник пояснює, як встановити та запустити dfieldX і pplaneX на Windows, macOS та Linux.

Ці програми вимагають Java 21 або новішої версії.

Попередня інсталяція Java не потрібна. Цей посібник передбачає, що Java ще не встановлена на вашому комп'ютері.

---

# 1. Завантаження програми

Завантажте найновіший реліз з GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Збережіть завантажений JAR-файл у місці, де його буде легко знайти пізніше, наприклад, на Робочому столі (Desktop) або в папці "Завантаження" (Downloads).

---

# 2. Windows

## Встановлення Java

1. Відкрийте веб-браузер.
2. Відвідайте: https://adoptium.net/
3. Завантажте Eclipse Temurin Java 21 LTS для Windows x64.
4. Запустіть завантажений інсталятор.
5. У розділі "Custom Setup" (Спеціальне налаштування) переконайтеся, що ввімкнено "Add to PATH" та "Associate .jar files".
6. Завершіть встановлення.

## Перевірка встановлення

Відкрийте Command Prompt (Командний рядок) і виконайте:

    java -version

Підтримується будь-яка версія 21 або новіша.

## Запуск програми

    java -jar dfieldX.jar

або

    java -jar pplaneX.jar

---

# 3. macOS

## Встановлення Java
1. Відвідайте https://adoptium.net/
2. Завантажте найновішу версію Java LTS (21 або новішу) для macOS.  
   * *Порада:* Вибирайте інсталятор `.dmg` (наприклад, `jdk-21_macos-x64_bin.dmg` для комп'ютерів Mac з процесорами Intel або еквівалент `aarch64` для Apple Silicon M1/M2/M3/M4).
3. Запустіть інсталятор і дотримуйтесь інструкцій на екрані.

## Організація програми
Для кращої організації рекомендуємо перемістити завантажені файли `dfieldX.jar` та `pplaneX.jar` до вашої папки **Applications** (Програми).

## Перший запуск (Обхід Gatekeeper)
macOS містить функцію безпеки, яка блокує програми від невідомих розробників. Якщо ви просто двічі клацнете файл `.jar` вперше, macOS покаже попередження і не дозволить його відкрити.

1. **Не клацайте двічі** по файлу першого разу.
2. Натомість **клацніть правою кнопкою миші** (або Control-клік) на файлі `.jar` і виберіть **Open** (Відкрити) з контекстного меню.
3. З'явиться діалогове вікно з проханням підтвердити. Знову натисніть **Open**.
4. **Звичайне використання:** Це потрібно зробити лише один раз. Для всіх наступних запусків ви можете просто двічі клацнути файл `.jar`, щоб запустити програму у звичайному режимі.

## Запуск через термінал (Альтернатива)
Якщо ви віддаєте перевагу командному рядку, відкрийте Terminal і виконайте:
    java -jar dfieldX.jar
або
    java -jar pplaneX.jar

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Встановлення Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Перевірка:

    java -version

Запуск:

    java -jar dfieldX.jar

або

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Встановлення Java:

    sudo dnf install java-21-openjdk

Перевірка:

    java -version

Запуск:

    java -jar dfieldX.jar

або

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

Встановлення Java:

    sudo pacman -S jre21-openjdk

Перевірка:

    java -version

Запуск:

    java -jar dfieldX.jar

або

    java -jar pplaneX.jar

---

# Усунення несправностей

## Нічого не відбувається при подвійному клацанні на JAR-файлі

Запустіть програму з терміналу:

    java -jar dfieldX.jar

або

    java -jar pplaneX.jar

## Помилка Unsupported Class Version Error

Встановіть Java 21 або новішої версії.

## Програма не запускається

Перевірте:

    java -version

і повідомте розробнику про будь-які повідомлення про помилки.
