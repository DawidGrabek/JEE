# Użyj oficjalnego obrazu Nginx
FROM nginx:latest

# Usuń domyślną konfigurację Nginx
RUN rm /etc/nginx/conf.d/default.conf

# Skopiuj zbudowane pliki React do folderu Nginx
COPY build /usr/share/nginx/html

# Port, na którym Nginx nasłuchuje
EXPOSE 80