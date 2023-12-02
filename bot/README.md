# Hectoc Bot

The Hectoc Bot in the is a Mastodon bot, that runs [@hourlyhectoc@botsin.space](https://botsin.space/@hourlyhectoc).
It posts a Hectoc every hour and can favorite correct answers. Wrong answers get a private commend.

### GitHub Actions

#### Verify

There is a GitHub [action](../.github/workflows/verify.yml), that runs the maven `verify` step for each event,
that changes the main branch like a push or a successful merge.

#### Build and deploy

There is a GitHub [action](../.github/workflows/build-and-deploy.yml), that builds the java projects,
creates a containerimage with it and redeploys it at the configured server.