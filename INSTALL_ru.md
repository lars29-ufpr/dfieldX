> **Language / Язык:** [English](INSTALL.md) | [Português](INSTALL_pt_BR.md) | [Español](INSTALL_es.md) | [Français](INSTALL_fr.md) | **Русский** | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | [Deutsch](INSTALL_de.md) | [Italiano](INSTALL_it.md)

# Руководство по установке

# dfieldX и pplaneX

Это руководство объясняет, как установить и запустить dfieldX и pplaneX в Windows, macOS и Linux.

Эти приложения требуют Java 21 или новее.

Предварительная установка Java не требуется. В этом руководстве предполагается, что Java еще не установлена на вашем компьютере.

---

# 1. Загрузка приложения

Загрузите последний релиз с GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Сохраните загруженный JAR-файл в месте, где вы сможете легко его найти позже, например, на Рабочем столе (Desktop) или в папке «Загрузки» (Downloads).

---

# 2. Windows

## Установка Java

1. Откройте ваш веб-браузер.
2. Посетите: https://adoptium.net/
3. Скачайте Eclipse Temurin Java 21 LTS для Windows x64.
4. Запустите скачанный установщик.
5. В «Custom Setup» (Пользовательская установка) убедитесь, что включены опции «Add to PATH» и «Associate .jar files».
6. Завершите установку.

## Проверка установки

Откройте командную строку (Command Prompt) и выполните:

    java -version

Поддерживается любая версия 21 или новее.

## Запуск приложения

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

---

# 3. macOS

## Установка Java
1. Посетите https://adoptium.net/
2. Скачайте последнюю версию Java LTS (21 или новее) для macOS.  
   * *Совет:* Выбирайте установщик `.dmg` (например, `jdk-21_macos-x64_bin.dmg` для Mac с процессорами Intel, или эквивалент `aarch64` для Apple Silicon M1/M2/M3/M4).
3. Запустите установщик и следуйте инструкциям на экране.

## Организация приложения
Для лучшей организации мы рекомендуем переместить загруженные файлы `dfieldX.jar` и `pplaneX.jar` в папку **Программы** (Applications).

## Первый запуск (Обход Gatekeeper)
macOS включает функцию безопасности, которая блокирует приложения от неустановленных разработчиков. Если вы просто дважды щелкнете файл `.jar` в первый раз, macOS покажет предупреждение и не позволит его открыть.

1. **Не щелкайте дважды** по файлу в первый раз.
2. Вместо этого **щелкните правой кнопкой мыши** (или Control-клик) по файлу `.jar` и выберите **Открыть** (Open) в контекстном меню.
3. Появится диалоговое окно с просьбой подтвердить. Снова нажмите **Открыть**.
4. **Обычное использование:** Это нужно сделать только один раз. При всех последующих использованиях вы можете просто дважды щелкнуть файл `.jar`, чтобы запустить приложение в обычном режиме.

## Запуск через терминал (Альтернатива)
Если вы предпочитаете командную строку, откройте Терминал и выполните:
    java -jar dfieldX.jar
или
    java -jar pplaneX.jar

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

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

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Установка Java:

    sudo dnf install java-21-openjdk

Проверка:

    java -version

Запуск:

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

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

## Ничего не происходит при двойном щелчке по JAR-файлу

Запустите приложение из терминала:

    java -jar dfieldX.jar

или

    java -jar pplaneX.jar

## Ошибка "Unsupported Class Version"

Установите Java 21 или новее.

## Приложение не запускается

Проверьте:

    java -version

и сообщите о любых сообщениях об ошибках разработчику.
