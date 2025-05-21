# Stadio 1: Build

FROM eclipse-temurin:21.0.3_9-jdk-jammy AS builder

#Imposta directory di lavoro utilizzando l'istruzione WORKDIR.
#Ciò specificherà dove verranno eseguiti i comandi futuri e i file della directory verranno copiati all'interno dell'immagine del container.
WORKDIR /opt/app

#Copia sia lo script wrapper Maven che il file pom.xml del tuo progetto nella directory di lavoro corrente /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod 755 ./mvnw

#Esegue il comando ./mvnw dependency:go-offline, che utilizza il wrapper Maven (./mvnw) per scaricare tutte le dipendenze
#per il tuo progetto senza creare il file JAR finale (utile per build più veloci).
RUN ./mvnw dependency:go-offline

#Copia il file sorgente
COPY ./src ./src

#Esegue la build con Maven
RUN ./mvnw clean install -P deploy


# Stadio 2: Runtime

FROM eclipse-temurin:21.0.3_9-jre-jammy AS final

#La creazione di un utente e di un gruppo di sistema per eseguire l'applicazione isola le autorizzazioni dell'applicazione e riduce al minimo i potenziali rischi per la sicurezza.
RUN adduser --system --group rc-user

#Imposta directory di lavoro utilizzando l'istruzione WORKDIR.
WORKDIR /opt/app

#Porta di esposizione
EXPOSE 8080

#Imposta l'utente
USER rc-user

#Copia l'artefatto dalla build precedente
COPY --from=builder --chown=rc-user:rc-user /opt/app/target/*.jar /opt/app/*.jar

#Entrypoint di partenza
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
