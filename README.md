# FixerCache

Small app working as a cache and proxy for requests to fixer.io.<br>
Supports only simple requests for currency rates where base currency is specified.<br>
An API key from fixer.io must be set as environment variable with the key "fixerAccessKey". <br>

The app will fetch current rates from fixed.io if the requested rates has not been recently cached.