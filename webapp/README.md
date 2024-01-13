# Hectoc Challenger

The Hectoc Challenger in the [webapp](../webapp) folder is a webapplication, that creates challenges and checks your answer.

## Build

### Local build
The projects are structured as multimodule maven project.
It should be sufficient to call `maven compile` in the parent directory.
To create executable jar files, call `maven package`

## Run
TBD...

### GitHub Actions

#### Verify

There is a GitHub [action](../.github/workflows/verify.yml), that runs the maven `verify` step for each event,
that changes the main branch like a push or a successful merge.

#### Build and deploy

There is a GitHub [action](../.github/workflows/build-and-deploy.yml), that builds the java projects,
creates a containerimage with it and redeploys it at the configured server.