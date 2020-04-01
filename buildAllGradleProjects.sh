set -e
for d in */ ; do
    if [[ -f "$d/gradlew" ]]; then
				$d/gradlew sonarqube -p $d
        $d/gradlew build -p $d
    fi
done
