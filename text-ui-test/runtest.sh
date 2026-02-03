#!/usr/bin/env bash
set -e

mkdir -p bin
javac -d bin src/main/java/*.java
java -cp bin Dick < text-ui-test/input.txt > text-ui-test/ACTUAL.TXT

if diff text-ui-test/ACTUAL.TXT text-ui-test/EXPECTED.TXT; then
  echo "Test result: PASSED"
else
  echo "Test result: FAILED"
  exit 1
fi
