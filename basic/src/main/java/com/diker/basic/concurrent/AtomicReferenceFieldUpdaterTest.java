package com.diker.basic.concurrent;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater 原子化更新类中的属性值
 * updater = AtomicReferenceFieldUpdater.newUpdater(p1, p2, p3) 其中p1为被更新的对象的Class,p2对应p1中需要更新的p3属性的Class,p3为p1中的属性字段名称
 * updater.compareAndSet(target, expect, update);其中target为被更新的对象，expect为属性的原值，update为属性的更新值，当target中的p3属性值为为p2时才能更新为p3
 * @author diker
 * @since 2018/12/6
 */
public class AtomicReferenceFieldUpdaterTest {

    public static void main(String[] args) {
        Node node = new Node("target");
        Node node2 = new Node("updated");
        node.compareAndSetRight( null, node2);
        System.out.println(node.getRight().getNodeValue());
    }

    private static class Node {
        private volatile Node left;
        private volatile Node right;
        private String nodeValue;
        private static final AtomicReferenceFieldUpdater leftUpdater = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "left");
        private static final AtomicReferenceFieldUpdater rightUpdater = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "right");

        public Node(String nodeValue) {
            this.nodeValue = nodeValue;
        }

        public boolean compareAndSetLeft(Node expect, Node update) {
            return leftUpdater.compareAndSet(this, expect, update);
        }

        public boolean compareAndSetRight(Node expect, Node update) {
            return rightUpdater.compareAndSet(this, expect, update);
        }

        public Node getLeft() {
            return (Node)leftUpdater.get(this);
        }

        public void setLeft(Node left) {
            leftUpdater.set(this, left);
        }

        public Node getRight() {
            return (Node)rightUpdater.get(this);
        }

        public void setRight(Node right) {
            rightUpdater.set(this, right);
        }

        public String getNodeValue() {
            return nodeValue;
        }

        public void setNodeValue(String nodeValue) {
            this.nodeValue = nodeValue;
        }
    }
}
