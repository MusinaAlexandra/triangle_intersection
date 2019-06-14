package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter points 'x y' for 1 triangle");
        double x;
        double y;

        x = scanner.nextDouble();
        y = scanner.nextDouble();
        Point p1 = new Point(x, y);
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        Point p2 = new Point(x, y);
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        Point p3 = new Point(x, y);
        Triangle t1 = new Triangle(p1, p2, p3);

        System.out.println("enter points 'x y' for 1 triangle");
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        Point p4 = new Point(x, y);
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        Point p5 = new Point(x, y);
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        Point p6 = new Point(x, y);
        Triangle t2 = new Triangle(p4, p5, p6);

        ArrayList<Point> intersec = findIntersection(t1,t2);
        for (Point p: intersec) {
            System.out.println(p.getX() + " " + p.getY());
        }

        System.out.println("Sq of intersection " + findSquare(intersec));
    }

    public static ArrayList<Point> findIntersection(Triangle t1, Triangle t2) {
        ArrayList<Point> result = new ArrayList<>();
        //ab
        Point ab_de = checkIntersec(t1.getP1(), t1.getP2(), t2.getP1(), t2.getP2());
        if (ab_de != null) {
            result.add(ab_de);
        }
        Point ab_df = checkIntersec(t1.getP1(), t1.getP2(), t2.getP1(), t2.getP3());
        if (ab_df != null) {
            result.add(ab_df);
        }
        Point ab_ef = checkIntersec(t1.getP1(), t1.getP2(), t2.getP2(), t2.getP3());
        if (ab_ef != null) {
            result.add(ab_ef);
        }
        //ac
        Point ac_de = checkIntersec(t1.getP1(), t1.getP3(), t2.getP1(), t2.getP2());
        if (ac_de != null) {
            result.add(ac_de);
        }
        Point ac_df = checkIntersec(t1.getP1(), t1.getP3(), t2.getP1(), t2.getP3());
        if (ac_df != null) {
            result.add(ac_df);
        }
        Point ac_ef = checkIntersec(t1.getP1(), t1.getP3(), t2.getP2(), t2.getP3());
        if (ac_ef != null) {
            result.add(ac_ef);
        }
        //bc
        Point bc_de = checkIntersec(t1.getP2(), t1.getP3(), t2.getP1(), t2.getP2());
        if (bc_de != null) {
            result.add(bc_de);
        }
        Point bc_df = checkIntersec(t1.getP2(), t1.getP3(), t2.getP1(), t2.getP3());
        if (bc_df != null) {
            result.add(bc_df);
        }
        Point bc_ef = checkIntersec(t1.getP2(), t1.getP3(), t2.getP2(), t2.getP3());
        if (bc_ef != null) {
            result.add(bc_ef);
        }
        //inner points of triangles
        if (checkInner(t1, t2.getP1()) && !containsPoint(result, t2.getP1())) {
            result.add(t2.getP1());
        }
        if (checkInner(t1, t2.getP2()) && !containsPoint(result, t2.getP2())) {
            result.add(t2.getP2());
        }
        if (checkInner(t1, t2.getP3()) && !containsPoint(result, t2.getP3())) {
            result.add(t2.getP3());
        }

        if (checkInner(t2, t1.getP1()) && !containsPoint(result, t1.getP1())) {
            result.add(t1.getP1());
        }
        if (checkInner(t2, t1.getP2()) && !containsPoint(result, t1.getP2())) {
            result.add(t1.getP2());
        }
        if (checkInner(t2, t1.getP3()) && !containsPoint(result, t1.getP3())) {
            result.add(t1.getP3());
        }
        return result;
    }

    public static boolean containsPoint(ArrayList<Point> arr, Point p) {
        for (Point pp: arr) {
            if (pp.getY() == p.getY() && pp.getX() == p.getX()) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkInner(Triangle t, Point p) {
        double k1 = (t.getP1().getX() - p.getY()) * (t.getP2().getY() - t.getP1().getY()) - (t.getP2().getX() - t.getP1().getX()) * (t.getP1().getY() - p.getY());
        double k2 = (t.getP1().getX() - p.getY()) * (t.getP2().getY() - t.getP1().getY()) - (t.getP2().getX() - t.getP1().getX()) * (t.getP1().getY() - p.getY());
        double k3 = (t.getP1().getX() - p.getY()) * (t.getP2().getY() - t.getP1().getY()) - (t.getP2().getX() - t.getP1().getX()) * (t.getP1().getY() - p.getY());
        return k1 > 0 && k2 > 0 && k3 > 0 || k1 < 0 && k2 < 0 && k3 < 0;
    }

    public static Point checkIntersec(Point a1, Point b1, Point a2, Point b2) {
        double tng1 = Triangle.tng(a1, b1);
        double tng2 = Triangle.tng(a2, b2);
        if (tng1 == tng2) {
            return null; // parallel
        }
        //intersection
        double x = -((a1.getX() * b1.getY() - b1.getX() * a1.getY()) * (b2.getX() - a2.getX()) - (a2.getX() * b2.getY() - b2.getX() * a2.getY()) * (b1.getX() - a1.getX())) /
                ((a1.getY() - b1.getY()) * (b2.getX() - a2.getX()) - (a2.getY() - b2.getY()) * (b1.getX() - a1.getX()));
        double y = ((a2.getY() - b2.getY()) * (-x) - (a2.getX() * b2.getY() - b2.getX() * a2.getY())) / (b2.getX() - a2.getX());
        if ((Math.min(a1.getX(),b1.getX()) <= x && Math.max(a1.getX(),b1.getX()) >= x &&
                Math.min(a2.getX(), b2.getX()) <= x && Math.max(a2.getX(), b2.getX()) >= x) &&
                (Math.min(a1.getY(), b1.getY()) <= y && Math.max(a1.getY(), b1.getY()) >= y &&
                        Math.min(a2.getY(), b2.getY()) <= y && Math.max(a2.getY(), b2.getY()) >= y)) {
            return new Point(x,y);
        }
        return null;
    }

    public static double findSquare(ArrayList<Point> listOfpoints) {
        double res = 0;
        for (int i = 0; i < listOfpoints.size() - 1; i++){
            res += listOfpoints.get(i).getX() * listOfpoints.get(i+1).getY();
        }
        res += listOfpoints.get(listOfpoints.size() - 1).getX() * listOfpoints.get(0).getY();

        for (int i = 0; i < listOfpoints.size() - 1; i++){
            res -= listOfpoints.get(i+1).getX()  * listOfpoints.get(i).getY();
        }
        res -= listOfpoints.get(0).getX() * listOfpoints.get(listOfpoints.size()-1).getY();
        return 0.5*Math.abs(res);
    }
}