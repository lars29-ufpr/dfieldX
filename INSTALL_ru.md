[English Version](INSTALL.md)

# Руководство по установке

# dfieldX и pplaneX

Это руководство объясняет, как установить и запустить dfieldX и pplaneX в системах Windows, macOS и Linux.

Для этих программ требуется Java 21 или новее.

Предварительная установка Java не требуется. Это руководство предполагает, что Java еще не установлена на вашем компьютере.

---

# 1. Загрузка программы

Скачайте последнюю версию с GitHub:

- dfieldX: [https://tinyurl.com/difieldx-ufpr](https://tinyurl.com/difieldx-ufpr)
- pplaneX: [https://tinyurl.com/pplanex-ufpr](https://tinyurl.com/pplanex-ufpr)

Сохраните загруженный JAR-файл в легкодоступном месте, например, на Рабочем столе или в папке Загрузки.

---

# 2. Windows

## Установка Java

1. Откройте ваш браузер.
2. Перейдите на: [https://adoptium.net/](https://adoptium.net/)
3. Скачайте Eclipse Temurin Java 21 LTS для Windows x64.
4. Запустите скачанный установщик.
5. На экране "Custom Setup" (Выборочная установка) убедитесь, что отмечены пункты "Add to PATH" и "Associate .jar files".
6. Завершите установку.

## Проверка установки

Откройте Командную строку (Prompt de Comando) и выполните:

    java -version

Поддерживается любая версия 21 или новее.

## Запуск программы

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

---

# 3. macOS

## Определение типа вашего Mac

Откройте «Об этом Mac» (About This Mac).

Выберите версию Apple Silicon для Mac с процессорами M1, M2, M3, M4 или новее, и версию Intel для Mac на базе Intel.

## Установка Java

1. Перейдите на [https://adoptium.net/](https://adoptium.net/)
2. Скачайте Java 21 LTS для macOS.
3. Запустите установщик.
4. Следуйте инструкциям на экране.

## Проверка установки

Откройте Терминал (Terminal) и выполните:

    java -version

## Запуск программы

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

## Если macOS блокирует приложение

Откройте:

Системные настройки (System Settings) → Конфиденциальность и безопасность (Privacy & Security) → Все равно открыть (Open Anyway)

---

# 4. Linux (Ubuntu, Debian и Linux Mint)

Установка Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Проверка:

    java -version

Запуск:

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux и AlmaLinux)

Установка Java:

    sudo dnf install java-21-openjdk

Проверка:

    java -version

Запуск:

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux и Manjaro)

Установка Java:

    sudo pacman -S jre21-openjdk

Проверка:

    java -version

Запуск:

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

---

# Устранение неполадок

## Ничего не происходит при двойном щелчке по файлу JAR

Запустите программу через терминал:

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

## Ошибка версии класса (Unsupported Class Version)

Установите Java 21 или новее.

## Программа не запускается

Проверьте:

    java -version

И сообщите разработчику о любом появившемся сообщении об ошибке.

---

# Дополнительная информация

dfieldX и pplaneX распространяются как независимые исполняемые JAR-файлы.

После установки Java 21 или новее дополнительная установка не требуется.

Просто сохраните JAR-файл в любой удобной папке и запускайте его, когда захотите.

Наслаждайтесь исследованием дифференциальных уравнений и динамических систем!

---

## 🍎 Предупреждение системы безопасности в macOS (Apple Gatekeeper)

Если при открытии файла .jar появляется предупреждение о том, что "Apple не может проверить этот файл на наличие вредоносного ПО", это происходит потому, что это свободное программное обеспечение и не имеет платной коммерческой подписи от Apple.

### Способ 1: Системные настройки (Графический интерфейс)
1. Нажмите **Готово** (Done) в окне предупреждения.
2. Откройте **Системные настройки** (System Settings) → **Конфиденциальность и безопасность** (Privacy & Security).
3. Прокрутите вниз до раздела **Безопасность** (Security).
4. Найдите предупреждение о блокировке файла .jar и нажмите **Все равно открыть** (Open Anyway).
5. Подтвердите с помощью пароля/Touch ID и нажмите **Открыть** (Open).

### Способ 2: Терминал
Удалите атрибут карантина macOS, выполнив:

    xattr -d com.apple.quarantine *.jar

Или запустите программу напрямую через терминал:

    java -jar *.jar
