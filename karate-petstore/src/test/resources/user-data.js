function() {
  var UUID = Java.type('java.util.UUID');
  var Random = Java.type('java.util.concurrent.ThreadLocalRandom');
  var userId = Random.current().nextLong(100000000000, 999999999999);
  return {
    username: 'karate-user-' + UUID.randomUUID().toString(),
    userId: userId
  };
}
