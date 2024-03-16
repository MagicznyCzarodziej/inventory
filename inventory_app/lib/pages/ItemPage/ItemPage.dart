import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:inventory_app/api/Items.dart';
import 'package:inventory_app/pages/ItemPage/EditItemPage.dart';
import 'package:inventory_app/pages/ItemPage/ItemPageBloc.dart';
import 'package:inventory_app/routes/simpleRoute.dart';
import 'package:inventory_app/utils.dart';

class ItemPage extends StatefulWidget {
  const ItemPage({super.key, required this.itemId});

  final String itemId;

  @override
  State<ItemPage> createState() => _ItemPageState();
}

class _ItemPageState extends State<ItemPage> {
  @override
  void initState() {
    super.initState();
    context.read<ItemPageBloc>().add(ItemPageEvent.fetchItem(widget.itemId));
  }

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<ItemPageBloc, ItemPageState>(
      builder: (context, state) {
        return Scaffold(
          resizeToAvoidBottomInset: false,
          body: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              if (state.photoBytes != null)
                // has photo
                Container(
                  height: 270,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      fit: BoxFit.cover,
                      image: MemoryImage(state.photoBytes!),
                    ),
                  ),
                )
              else if (state.photoState == PhotoState.fetching)
                const SizedBox(
                  height: 270,
                  child: Center(
                    child: CircularProgressIndicator(),
                  ),
                )
              else
                SafeArea(child: Container()),
              Expanded(
                child: state.item != null
                    ? Container(
                        decoration: BoxDecoration(
                          color: const Color.fromRGBO(250, 250, 250, 1),
                          boxShadow: state.item!.photoUrl != null
                              ? [
                                  const BoxShadow(
                                    color: Colors.black54,
                                    blurRadius: 20,
                                    blurStyle: BlurStyle.outer,
                                  ),
                                ]
                              : [],
                        ),
                        child: Padding(
                          padding: const EdgeInsets.only(left: 24, right: 16, top: 8),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Row(
                                children: [
                                  Text(
                                    state.item!.category.name,
                                    style: TextStyle(
                                      color: Theme.of(context).colorScheme.onSurface.withAlpha(100),
                                      fontSize: 20,
                                    ),
                                  ),
                                  const Spacer(),
                                  IconButton(
                                    onPressed: () async {
                                      await deleteItem(state.item!.id);
                                      if (!mounted) return;
                                      Navigator.pop(context);
                                    },
                                    icon: Icon(
                                      Icons.delete,
                                      color: Theme.of(context).colorScheme.error,
                                    ),
                                  ),
                                  IconButton(
                                    onPressed: () => {},
                                    icon: Icon(
                                      Icons.history,
                                      color: Theme.of(context).colorScheme.primary,
                                    ),
                                  ),
                                  IconButton(
                                    onPressed: () {
                                      Navigator.push(
                                        context,
                                        simpleRoute(
                                          EditItemPage(itemId: state.item!.id),
                                        ),
                                      ).then((value) {
                                        context.read<ItemPageBloc>().add(ItemPageEvent.fetchItem(widget.itemId));
                                      });
                                    },
                                    icon: Icon(
                                      Icons.edit,
                                      color: Theme.of(context).colorScheme.primary,
                                    ),
                                  ),
                                ],
                              ),
                              if (state.item!.parentItem != null)
                                Text(
                                  state.item!.parentItem!.name,
                                  style: const TextStyle(fontSize: 40),
                                ),
                              Wrap(
                                children: [
                                  Text(
                                    "${state.item!.name}${state.item!.brand != null ? " " : ""}",
                                    style: const TextStyle(
                                      fontSize: 32,
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                  if (state.item!.brand != null)
                                    Text(
                                      state.item!.brand!,
                                      style: const TextStyle(fontSize: 32),
                                    ),
                                ],
                              ),
                              if (state.item!.description != null)
                                Padding(
                                  padding: const EdgeInsets.only(top: 8),
                                  child: Text(
                                    state.item!.description!,
                                    style: TextStyle(
                                      fontSize: 22,
                                      color: Theme.of(context).colorScheme.onSurfaceVariant,
                                    ),
                                  ),
                                ),
                              const Spacer(),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.baseline,
                                textBaseline: TextBaseline.alphabetic,
                                children: [
                                  Text(
                                    state.item!.currentStock.toString(),
                                    style: TextStyle(
                                      height: 0.8,
                                      fontSize: 200,
                                      color: getCurrentStockColor(state.item!.currentStock, state.item!.desiredStock),
                                    ),
                                  ),
                                  Text(
                                    "/${state.item!.desiredStock.toString()}",
                                    style: TextStyle(fontSize: 70, color: Colors.grey[400]),
                                  ),
                                ],
                              ),
                              const Spacer(),
                              if (state.item!.barcode != null)
                                Padding(
                                  padding: const EdgeInsets.only(bottom: 8),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      const Padding(
                                        padding: EdgeInsets.only(right: 4),
                                        child: Icon(CupertinoIcons.barcode),
                                      ),
                                      Text(state.item!.barcode!),
                                      TextButton(
                                        onPressed: () => {},
                                        child: Text(
                                          "Porównaj",
                                          style: TextStyle(
                                            color: Theme.of(context).colorScheme.primary,
                                          ),
                                        ),
                                      )
                                    ],
                                  ),
                                ),
                            ],
                          ),
                        ),
                      )
                    : const Center(
                        child: CircularProgressIndicator(),
                      ),
              ),
            ],
          ),
          bottomNavigationBar: BottomAppBar(
            padding: const EdgeInsets.symmetric(horizontal: 8),
            height: 70,
            child: state.item != null
                ? Row(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: [
                      if (state.item!.currentStock > 0)
                        IconButton(
                          onPressed: () =>
                              {context.read<ItemPageBloc>().add(ItemPageEvent.updateCurrentStock(state.item!.id, -1))},
                          icon: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Icon(
                              Icons.remove,
                              color: Theme.of(context).colorScheme.primary,
                            ),
                          ),
                        ),
                      if (state.item!.currentStock > 0)
                        Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 8),
                          child: TextButton(
                            onPressed: () => {
                              context
                                  .read<ItemPageBloc>()
                                  .add(ItemPageEvent.updateCurrentStock(state.item!.id, -state.item!.currentStock))
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(8),
                              child: Text(
                                "Nie ma",
                                style: TextStyle(
                                  color: Theme.of(context).colorScheme.primary,
                                ),
                              ),
                            ),
                          ),
                        ),
                      IconButton(
                        onPressed: () =>
                            {context.read<ItemPageBloc>().add(ItemPageEvent.updateCurrentStock(state.item!.id, 1))},
                        icon: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Icon(
                            Icons.add,
                            color: Theme.of(context).colorScheme.primary,
                          ),
                        ),
                      ),
                    ],
                  )
                : Row(),
          ),
        );
      },
    );
  }
}

class ItemPageWrapper extends StatelessWidget {
  const ItemPageWrapper({super.key, required this.child});

  final Widget child;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => ItemPageBloc(),
      child: child,
    );
  }
}
