#!/bin/bash

#./gradlew build || exit 1
#./gradlew cloverAggregateReport || exit 1
#scripts/coverage_summary.sh
#cp -r build/reports/clover/html/* /coverage-out/ || exit 1

./gradlew build || exit 1
./gradlew cloverGenerateReport || exit 1
./gradlew cloverAggregateReports || exit 1
scripts/coverage_summary.sh
ls -l /
ls -l /coverage-out/
cp -r build/reports/clover/html/* /coverage-out/ || exit 1
