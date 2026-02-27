# Kotlin PR Challenge

## Context

Imagine Pret is building a new 'friends' feature on the app, where customers can connect to share loyalty rewards, and more.

Initially the requirement for this API was just to load immediate connections, but now we want to expand the feature so that customers can explore who their friends' friends are, then continue exploring the network of friends, like a social network.

The PR changes the response of the API endpoint so that it now returns a tree structure of friends that will be sent back to the Pret app.

Run it locally, see it in action, and review the changes.

## Reviewing the PR

Please be prepared to spend ~30 minutes discussing the PR and your feedback during the interview.

Add as much or as little detail as you like. You can use any comments that you write as notes during the discussion.

**NOTE: You do not need to change the code itself. Fixing any issues that you find is not part of the challenge.**

1. [View the changes](https://github.com/pretamanger/pull-request-challenge-kotlin/pull/1/files).
1. Clone or download the repository.
1. Add your review comments to `FriendsRoute.kt`.

## Running the API

Prerequisites:

- Java 17+

Run:

```bash
./gradlew run
```

Then send a `GET` request to:

- `http://localhost:3000/friends?userId=1`

### Notes

- The API defaults to port `3000` (configurable via `PORT`).
- `GET /friends` proxies to an “external friends service” endpoint:
  - `GET /external-friends-service?userId=...`
  - Base URL configurable via `EXTERNAL_FRIENDS_SERVICE_BASE_URL`.
