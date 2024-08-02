FROM php:8.0.3-apache-buster

RUN apt-get update

RUN docker-php-ext-install mysqli && docker-php-ext-enable mysqli
RUN a2enmod rewrite

COPY docker-config/000-default.conf /etc/apache2/sites-available/000-default.conf
COPY docker-config/my-php.ini /usr/local/etc/php/conf.d/my-php.ini
