FROM prom/prometheus
RUN rm -f /etc/prometheus/prometheus.yml
ADD config/ /etc/prometheus/
COPY entrypoint.sh /usr/local/bin/
ENTRYPOINT ["entrypoint.sh"]
