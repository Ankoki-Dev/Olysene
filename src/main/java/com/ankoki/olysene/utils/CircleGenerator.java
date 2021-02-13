package com.ankoki.olysene.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Location;

/**
 * @author AntivirusDev
 * I struggle alot with maths so finding this resource helped, tyty<3
 */
public class CircleGenerator {

    private final static int MAX_SQUARE = 1000;

    private static final double SQUARE_ROOT_OF_TWO = 1.4142135624;

    private static final HashMap<Integer, HashSet<RelativeLocation>> cacheXZEnclosed = new HashMap<>();

    private static final HashMap<Integer, HashSet<RelativeLocation>> cacheXYEnclosed = new HashMap<>();

    private static final HashMap<Integer, HashSet<RelativeLocation>> cacheZYEnclosed = new HashMap<>();

    private static final HashMap<Integer, HashSet<RelativeLocation>> cacheXZIgnoreEnclosed = new HashMap<>();

    private static final HashMap<Integer, HashSet<RelativeLocation>> cacheXYIgnoreEnclosed = new HashMap<>();

    private static final HashMap<Integer, HashSet<RelativeLocation>> cacheZYIgnoreEnclosed = new HashMap<>();

    private static final HashMap<Integer, Integer> squareRootCache = new HashMap<Integer, Integer>();

    static {
        for (int i = 0; i <= MAX_SQUARE; i++) {

            squareRootCache.put(i * i, i);

        }

        int greatestPerfectSquare = 0;

        for (int i = 0; i <= MAX_SQUARE * MAX_SQUARE; i++) {

            if (squareRootCache.containsKey(i)) {
                greatestPerfectSquare = squareRootCache.get(i);
            } else {
                squareRootCache.put(i, greatestPerfectSquare);
            }
        }
    }

    private static class RelativeLocation {

        int rX, rY, rZ;

        public RelativeLocation(int relX, int relY, int relZ) {
            this.rY = relY;
            this.rZ = relZ;
            this.rX = relX;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof RelativeLocation))
                return false;

            RelativeLocation rl = (RelativeLocation) o;
            return rl.rX == rX && rl.rY == rY && rl.rZ == rZ;

        }

        @Override
        public int hashCode() {

            return rX * rX + rY * rY + rZ * rZ;

        }

    }

    public static enum Plane {
        XZ, XY, ZY
    }

    /**
     * Generates a set of locations that represent a rasterized circle. Uses cached
     * results to improve efficiency
     *
     * @param center         - the center point of the circle
     * @param radius         - the radius of the circle
     * @param plane          - which plane to generate the circle on (Plane enum
     *                       included in CircleGen class)
     * @param ignoreEnclosed - whether to ignore 'enclosed' squares, or those which
     *                       have a neighboring square in both axial directions of
     *                       the circle's plane
     *                       @param allowBurrs - whether the circle should have 'burrs' or single blocks jutting out at the tips of the axes
     * @return a set of locations representing a rasterized circle
     * @throws IllegalArgumentException if center point is null, plane is null, or
     *                                  radius is less than zero
     */
    public static HashSet<Location> generateCircle(Location center, int radius, Plane plane, boolean ignoreEnclosed, boolean allowBurrs) {

        if (center == null || radius < 0 || plane == null)
            throw new IllegalArgumentException("Incorrect parameter(s)!");

        HashSet<Location> set = new HashSet<>();

        HashMap<Integer, HashSet<RelativeLocation>> whichCacheToUse = null;
        switch (plane) {
            case XZ:
                whichCacheToUse = ignoreEnclosed ? cacheXZIgnoreEnclosed : cacheXZEnclosed;
                break;
            case XY:
                whichCacheToUse = ignoreEnclosed ? cacheXYIgnoreEnclosed : cacheXYEnclosed;
                break;
            case ZY:
                whichCacheToUse = ignoreEnclosed ? cacheZYIgnoreEnclosed : cacheZYEnclosed;
                break;
            default:
                break;
        }

        if (whichCacheToUse.containsKey(radius)) {

            HashSet<RelativeLocation> locs = whichCacheToUse.get(radius);
            locs = (HashSet<RelativeLocation>) locs.clone();


            if(!allowBurrs) {

                switch (plane) {
                    case XZ:
                        locs.remove(new RelativeLocation(0,0, radius));
                        locs.add(new RelativeLocation(0, 0, radius-1));

                        locs.remove(new RelativeLocation(-radius,0, 0));
                        locs.add(new RelativeLocation(-radius+1, 0, 0));

                        locs.remove(new RelativeLocation(radius,0, 0));
                        locs.add(new RelativeLocation( radius-1, 0, 0));

                        locs.remove(new RelativeLocation(0,0, -radius));
                        locs.add(new RelativeLocation(0, 0, -radius+1));
                        break;
                    case XY:
                        locs.remove(new RelativeLocation(0,radius, 0));
                        locs.add(new RelativeLocation(0, radius-1, 0));

                        locs.remove(new RelativeLocation(0,-radius, 0));
                        locs.add(new RelativeLocation(0, -radius+1, 0));

                        locs.remove(new RelativeLocation(radius,0, 0));
                        locs.add(new RelativeLocation( radius-1, 0, 0));
                        locs.remove(new RelativeLocation(-radius,0, 0));
                        locs.add(new RelativeLocation( -radius+1, 0, 0));

                        break;
                    case ZY:
                        locs.remove(new RelativeLocation(0,radius, 0));
                        locs.add(new RelativeLocation(0, radius-1, 0));

                        locs.remove(new RelativeLocation(0,-radius, 0));
                        locs.add(new RelativeLocation(0, -radius+1, 0));

                        locs.remove(new RelativeLocation(0,0, radius));
                        locs.add(new RelativeLocation(0 , 0, radius-1));
                        locs.remove(new RelativeLocation(0,0, -radius));
                        locs.add(new RelativeLocation( 0, 0, -radius+1));
                        break;
                    default:
                        break;
                }

            }
            for (RelativeLocation rl : locs)
                set.add(new Location(center.getWorld(), center.getX() + rl.rX, center.getY() + rl.rY,
                        center.getZ() + rl.rZ));
            return set;
        }
        HashSet<RelativeLocation> locs = new HashSet<>();

        HashSet<RelativeLocation> remainingOctants = new HashSet<>();

        final int unchanging_axis = 0;
        int radiusSquared = radius * radius;

        int stopValue = ((int) (radius / SQUARE_ROOT_OF_TWO)) + 1;
        RelativeLocation lastAdded = null;
        switch (plane) {
            case XZ:

                for (int ind = 0; ind < stopValue; ind++) {
                    int prevDep = squareRootCache.get(radiusSquared - (ind * ind));
                    int nextDep = squareRootCache.get(radiusSquared - ((ind + 1) * (ind + 1)));

                    for (int i = 0; i <= (prevDep - nextDep); i++) {

                        RelativeLocation add = new RelativeLocation(ind, unchanging_axis, prevDep - i);
                        locs.add(add);
                        lastAdded = add;
                    }
                }

                if (ignoreEnclosed) {
                    locs.remove(lastAdded);
                    Iterator<RelativeLocation> iteratorXZ = locs.iterator();

                    while (iteratorXZ.hasNext()) {

                        RelativeLocation rl = iteratorXZ.next();

                        if (locs.contains(new RelativeLocation(rl.rX + 1, rl.rY, rl.rZ))
                                && locs.contains(new RelativeLocation(rl.rX, rl.rY, rl.rZ + 1))) {

                            iteratorXZ.remove();
                        }

                    }
                }

                for (RelativeLocation rl : locs) {
                    fillOctantsXZ(remainingOctants, rl);
                }
                break;
            case XY:

                for (int ind = 0; ind < stopValue; ind++) {
                    int prevDep = squareRootCache.get(radiusSquared - (ind * ind));
                    int nextDep = squareRootCache.get(radiusSquared - ((ind + 1) * (ind + 1)));

                    for (int i = 0; i <= (prevDep - nextDep); i++) {
                        RelativeLocation add = new RelativeLocation(ind, prevDep - i, unchanging_axis);
                        locs.add(add);
                        lastAdded = add;
                    }
                }

                if (ignoreEnclosed) {
                    locs.remove(lastAdded);
                    Iterator<RelativeLocation> iteratorXY = locs.iterator();

                    while (iteratorXY.hasNext()) {

                        RelativeLocation rl = iteratorXY.next();
                        if (locs.contains(new RelativeLocation(rl.rX + 1, rl.rY, rl.rZ))
                                && locs.contains(new RelativeLocation(rl.rX, rl.rY + 1, rl.rZ))) {

                            iteratorXY.remove();
                        }

                    }
                }

                for (RelativeLocation rl : locs) {

                    fillOctantsXY(remainingOctants, rl);
                }
                break;
            case ZY:

                for (int ind = 0; ind < stopValue; ind++) {
                    int prevDep = squareRootCache.get(radiusSquared - (ind * ind));
                    int nextDep = squareRootCache.get(radiusSquared - ((ind + 1) * (ind + 1)));

                    for (int i = 0; i <= (prevDep - nextDep); i++) {
                        RelativeLocation add = new RelativeLocation(unchanging_axis, prevDep - i, ind);
                        locs.add(add);
                        lastAdded = add;
                    }
                }

                if (ignoreEnclosed) {
                    locs.remove(lastAdded);
                    Iterator<RelativeLocation> iteratorZY = locs.iterator();

                    while (iteratorZY.hasNext()) {

                        RelativeLocation rl = iteratorZY.next();
                        if (locs.contains(new RelativeLocation(rl.rX, rl.rY, rl.rZ + 1))
                                && locs.contains(new RelativeLocation(rl.rX, rl.rY + 1, rl.rZ))) {

                            iteratorZY.remove();
                        }

                    }
                }

                for (RelativeLocation rl : locs) {

                    fillOctantsZY(remainingOctants, rl);
                }
                break;
        }

        locs.addAll(remainingOctants);

        whichCacheToUse.put(radius, locs);

        if(!allowBurrs) {
            return generateCircle(center, radius, plane, ignoreEnclosed, allowBurrs);
        }

        for (RelativeLocation rl : locs)
            set.add(new Location(center.getWorld(), center.getBlockX() + rl.rX, center.getBlockY() + rl.rY,
                    center.getBlockZ() + rl.rZ));

        return set;
    }

    private static void fillOctantsXZ(Set<RelativeLocation> set, RelativeLocation location) {
        set.add(new RelativeLocation(location.rZ, location.rY, location.rX)); // octant 1
        set.add(new RelativeLocation(-location.rX, location.rY, location.rZ)); // octant 3
        set.add(new RelativeLocation(-location.rZ, location.rY, location.rX)); // octant 4
        set.add(new RelativeLocation(-location.rZ, location.rY, -location.rX)); // octant 5
        set.add(new RelativeLocation(-location.rX, location.rY, -location.rZ)); // octant 6
        set.add(new RelativeLocation(location.rX, location.rY, -location.rZ)); // octant 7
        set.add(new RelativeLocation(location.rZ, location.rY, -location.rX)); // octant 8
    }

    private static void fillOctantsZY(Set<RelativeLocation> set, RelativeLocation location) {
        set.add(new RelativeLocation(location.rX, location.rZ, location.rY)); // octant 1
        set.add(new RelativeLocation(location.rX, -location.rY, location.rZ)); // octant 3
        set.add(new RelativeLocation(location.rX, -location.rZ, location.rY)); // octant 4
        set.add(new RelativeLocation(location.rX, -location.rZ, -location.rY)); // octant 5
        set.add(new RelativeLocation(location.rX, -location.rY, -location.rZ)); // octant 6
        set.add(new RelativeLocation(location.rX, location.rY, -location.rZ)); // octant 7
        set.add(new RelativeLocation(location.rX, location.rZ, -location.rY)); // octant 8
    }

    private static void fillOctantsXY(Set<RelativeLocation> set, RelativeLocation location) {
        set.add(new RelativeLocation(location.rY, location.rX, location.rZ)); // octant 1
        set.add(new RelativeLocation(-location.rX, location.rY, location.rZ)); // octant 3
        set.add(new RelativeLocation(-location.rY, location.rX, location.rZ)); // octant 4
        set.add(new RelativeLocation(-location.rY, -location.rX, location.rZ)); // octant 5
        set.add(new RelativeLocation(-location.rX, -location.rY, location.rZ)); // octant 6
        set.add(new RelativeLocation(location.rX, -location.rY, location.rZ)); // octant 7
        set.add(new RelativeLocation(location.rY, -location.rX, location.rZ)); // octant 8
    }
}
