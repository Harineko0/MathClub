//Javaで二分探索木（木構造）を実装する
//http://4geek.net/implement-the-binary-search-tree-in-java/

package jp.mincra.mathclub.util;

class Node {
    int data;
    Node left;
    Node right;
}

public class BinaryTreeUtil {
    private Node root;

    public void add(int data) {
        // ツリーがない場合の特別処理
        if (root == null) {
            root = new Node();
            root.data = data;
            return;
        }

        // 現在ノードをルートノードとする
        Node current = root;
        while (true) {
            // 「追加する値 ＜ 現在ノードの値」なら左の子を対象とする
            if (data < current.data) {
                // 次のノードが存在しない場合はその位置にデータを追加する
                if (current.left == null) {
                    current.left = new Node();
                    current.left.data = data;
                    return;
                }
                // 存在する場合は次のノードを現在ノードとして繰り返す
                current = current.left;
            }
            // 「現在ノードの値 ≦ 追加する値」なら右の子を対象とする
            else if (current.data <= data) {
                if (current.right == null) {
                    current.right = new Node();
                    current.right.data = data;
                    return;
                }
                current = current.right;
            }
        }
    }

    public boolean contains(int data) {
        // 現在ノードをルートノードとする
        Node current = root;
        // 次のノードが存在しない場合は探索終了
        while (current != null) {
            // 等しければ探索終了
            if (current.data == data) {
                return true;
            }
            // 「探索値 ＜ 現在ノードの値」なら左の子に進む
            else if (data < current.data) {
                current = current.left;
            }
            // 「現在ノードの値 ＜ 探索値」なら右の子に進む
            else if (current.data < data) {
                current = current.right;
            }
        }
        return false;
    }

    public void remove(int data) {
        Node parent = null;
        Node current = root;
        while (current != null) {
            if (current.data == data) {
                if (current.left == null && current.right == null) {
                    // 子ノードなし→ノードをそのまま削除（親ノードからの参照を外す）
                    if (current == root) {
                        root = null;
                    } else {
                        updateLink(parent, current, null);
                    }
                } else if (current.left != null && current.right == null) {
                    // 子ノードは左のみ→子ノードが親ノードに直接参照されるようにする
                    if (current == root) {
                        root = root.left;
                    } else {
                        updateLink(parent, current, current.left);
                    }
                } else if (current.left == null && current.right != null) {
                    // 子ノードは右のみ→子ノードが親ノードに直接参照されるようにする
                    if (current == root) {
                        root = root.right;
                    } else {
                        updateLink(parent, current, current.right);
                    }
                } else {
                    // 子ノードが2つの場合

                    // まず削除対象ノードの右部分木の最小ノードを取得（左に進めば最小値が得られる）
                    Node min_parent = current;
                    Node min = current.right;
                    while (min.left != null) {
                        min_parent = min;
                        min = min.left;
                    }

                    // 最小ノードの右ノードを最小ノードの親が直接参照するようにする
                    // 削除対象がparentか（左に進んだか）で場合分けをする必要がある
                    if (min_parent == current) {
                        min_parent.right = min.right;
                    } else {
                        min_parent.left = min.right;
                    }

                    // 最小ノードを削除対象ノードに置き換える（削除対象ノードの左右の子を引き継ぐ）
                    min.left = current.left;
                    min.right = current.right;
                    if (current == root) {
                        root = min;
                    } else {
                        updateLink(parent, current, min);
                    }
                }
                return;
            } else if (data < current.data) {
                parent = current;
                current = current.left;
            } else if (current.data < data) {
                parent = current;
                current = current.right;
            }
        }
    }

    private void updateLink(Node parent, Node target, Node replace) {
        if (parent.left == target) {
            parent.left = replace;
        } else if (parent.right == target) {
            parent.right = replace;
        }
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node n) {
        if (n != null) {
            inOrder(n.left);
            System.out.print(n.data + ", ");
            inOrder(n.right);
        }
    }
}