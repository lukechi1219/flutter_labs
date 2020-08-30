import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  HomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  //
  static int numberInRow = 11;
  int numberOfSquares = numberInRow * 17;
  int player = 166; // PacMan start position

  List<int> playerEatenPath = [];

  void walkThrouth() {
    playerEatenPath.add(player);
    // while () {
    //   player++;
    // }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[800],
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Expanded(
            flex: 5,
            child: Container(
              child: GridView.builder(
                  physics: NeverScrollableScrollPhysics(),
                  itemCount: numberOfSquares,
                  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: numberInRow),
                  itemBuilder: (BuildContext context, int _index) {
                    //
                    if (_index == player) {
                      return MyPlayer();
                    } else if (_index <= 10 ||
                        _index >= 176 ||
                        _index % 11 == 0 ||
                        (_index - 10) % 11 == 0) {
                      //
                      print(_index);

                      return MyPixel(
                        child: Container(
                          color: Colors.blue,
                        ),
                      );
                    } else {
                      return MyPath(
                        outerColor: Colors.black,
                        innerColor: Colors.yellow,
                      );
                    }
                  }),
            ),
          ),
          Expanded(
            child: Container(
              color: Colors.pink,
            ),
          ),
        ],
      ),
    );
  }
}

class MyPlayer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Image.asset(
        'images/pacman.png');
  }
}

class MyPixel extends StatelessWidget {
  const MyPixel({
    Key key,
    @required Container child,
  })
      : _child = child,
        super(key: key);

  final _child;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(1.0),
      child: _child,
    );
  }
}

class MyPath extends StatelessWidget {
  const MyPath({
    Key key,
    @required Color outerColor,
    @required Color innerColor,
  })
      : _outerColor = outerColor,
        _innerColor = innerColor,
        super(key: key);

  final _outerColor;
  final _innerColor;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(1.0),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(6),
        child: Container(
          padding: EdgeInsets.all(10),
          color: _outerColor,
          child: ClipRRect(
            borderRadius: BorderRadius.circular(10),
            child: Container(
              color: _innerColor,
            ),
          ),
        ),
      ),
    );
  }
}
