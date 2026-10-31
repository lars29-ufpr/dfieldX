> **Language / Lingua:** [English](INSTALL.md) | [Português](INSTALL_pt_BR.md) | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | [Deutsch](INSTALL_de.md) | **Italiano**

# Guida all'installazione

# dfieldX e pplaneX

Questa guida spiega come installare ed eseguire dfieldX e pplaneX su Windows, macOS e Linux.

Queste applicazioni richiedono Java 21 o versioni successive.

Non è richiesta alcuna installazione precedente di Java. Questa guida presuppone che Java non sia ancora installato sul tuo computer.

---

# 1. Download dell'Applicazione

Scarica l'ultima release da GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Salva il file JAR scaricato in una posizione facile da trovare in seguito, come il Desktop o la cartella Download.

---

# 2. Windows

## Installa Java

1. Apri il tuo browser web.
2. Visita: https://adoptium.net/
3. Scarica Eclipse Temurin Java 21 LTS per Windows x64.
4. Esegui il programma di installazione scaricato.
5. In "Custom Setup" (Installazione personalizzata), assicurati che le opzioni "Add to PATH" e "Associate .jar files" siano abilitate.
6. Completa l'installazione.

## Verifica l'Installazione

Apri il Prompt dei comandi ed esegui:

    java -version

È supportata qualsiasi versione 21 o successiva.

## Esegui l'Applicazione

    java -jar dfieldX.jar

oppure

    java -jar pplaneX.jar

---

# 3. macOS

## Installa Java
1. Visita https://adoptium.net/
2. Scarica l'ultima versione di Java LTS (21 o successiva) per macOS.  
   * *Suggerimento:* Scegli il programma di installazione `.dmg` (es. `jdk-21_macos-x64_bin.dmg` per Mac Intel, o l'equivalente `aarch64` per Apple Silicon M1/M2/M3/M4).
3. Esegui il programma di installazione e segui le istruzioni sullo schermo.

## Organizza l'Applicazione
Per una migliore organizzazione, consigliamo di spostare i file `dfieldX.jar` e `pplaneX.jar` scaricati nella cartella **Applicazioni** (Applications).

## Primo Avvio (Bypassare Gatekeeper)
macOS include una funzione di sicurezza che blocca le applicazioni provenienti da sviluppatori non identificati. Se fai semplicemente doppio clic sul file `.jar` per la prima volta, macOS mostrerà un avviso e ne impedirà l'apertura.

1. **Non fare doppio clic** sul file la prima volta.
2. Invece, fai **clic con il tasto destro** (o Control-clic) sul file `.jar` e seleziona **Apri** (Open) dal menu contestuale.
3. Apparirà una finestra di dialogo che ti chiederà di confermare. Clicca di nuovo su **Apri**.
4. **Uso Normale:** Dovrai farlo solo una volta. Per tutti gli utilizzi successivi, potrai semplicemente fare doppio clic sul file `.jar` per avviare l'applicazione normalmente.

## Esecuzione tramite Terminale (Alternativa)
Se preferisci la riga di comando, apri il Terminale ed esegui:
    java -jar dfieldX.jar
oppure
    java -jar pplaneX.jar

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Installa Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Verifica:

    java -version

Esegui:

    java -jar dfieldX.jar

oppure

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Installa Java:

    sudo dnf install java-21-openjdk

Verifica:

    java -version

Esegui:

    java -jar dfieldX.jar

oppure

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

Installa Java:

    sudo pacman -S jre21-openjdk

Verifica:

    java -version

Esegui:

    java -jar dfieldX.jar

oppure

    java -jar pplaneX.jar

---

# Risoluzione dei Problemi

## Non succede nulla facendo doppio clic sul file JAR

Esegui l'applicazione da un terminale:

    java -jar dfieldX.jar

oppure

    java -jar pplaneX.jar

## Errore Unsupported Class Version

Installa Java 21 o versioni successive.

## L'applicazione non si avvia

Verifica:

    java -version

e segnala eventuali messaggi di errore allo sviluppatore.
