for d in */ ; do
    if [[ -f "$d/gradlew" ]]; then
        $d/gradlew build -p $d
    fi
done
