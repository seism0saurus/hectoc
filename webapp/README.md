# Hectoc Challenger

The Hectoc Challenger in the [webapp](./webapp) folder is a webapplication, that creates challenges and checks your answer.

## Build

### Local build
The projects are structured as multimodule maven project.
It should be sufficient to call `maven compile` in the parent directory.
To create executable jar files, call `maven package`

## Run
TBD...

### GitHub Actions
There is a GitHub [action](../.github/workflows/webapp-verify.yml), that runs the maven `verify` step for each event, that changes the main [pom.xml](../pom.xml),
the [shared library](../shared) or this webapp.