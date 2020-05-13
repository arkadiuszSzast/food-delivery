#!/bin/sh
/bin/echo $ACTUATOR_PASSWORD > /etc/prometheus/actuator_password
exec /bin/prometheus $@
