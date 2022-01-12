package leetcode.editor.cn.l_1036;
//在一个 10⁶ x 10⁶ 的网格中，每个网格上方格的坐标为 (x, y) 。
//
// 现在从源方格 source = [sx, sy] 开始出发，意图赶往目标方格 target = [tx, ty] 。数组 blocked 是封锁的方格列表
//，其中每个 blocked[i] = [xi, yi] 表示坐标为 (xi, yi) 的方格是禁止通行的。 
//
// 每次移动，都可以走到网格中在四个方向上相邻的方格，只要该方格 不 在给出的封锁列表 blocked 上。同时，不允许走出网格。 
//
// 只有在可以通过一系列的移动从源方格 source 到达目标方格 target 时才返回 true。否则，返回 false。 
//
// 
//
// 示例 1： 
//
// 
//输入：blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
//输出：false
//解释：
//从源方格无法到达目标方格，因为我们无法在网格中移动。
//无法向北或者向东移动是因为方格禁止通行。
//无法向南或者向西移动是因为不能走出网格。 
//
// 示例 2： 
//
// 
//输入：blocked = [], source = [0,0], target = [999999,999999]
//输出：true
//解释：
//因为没有方格被封锁，所以一定可以到达目标方格。
// 
//
// 
//
// 提示： 
//
// 
// 0 <= blocked.length <= 200 
// blocked[i].length == 2 
// 0 <= xi, yi < 10⁶ 
// source.length == target.length == 2 
// 0 <= sx, sy, tx, ty < 10⁶ 
// source != target 
// 题目数据保证 source 和 target 不在封锁列表内 
// 
// Related Topics 深度优先搜索 广度优先搜索 数组 哈希表 👍 121 👎 0


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    final int EDGE = (int) 1E6;
    int MAX = (int) 1E5;
    long BASE = 131L; // BASE作为hash碰撞的一个取值，是随意取的质数，目的是将二维数组坐标映射到一维上
    final int[][] dir = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    final Set<Long> blockedSet = new HashSet<>();

    public boolean isEscapePossible(int[][] blocked, int[] s, int[] t) {
        int n = blocked.length;
        MAX = n * (n - 1) / 2;
        for (int[] per : blocked) {
            blockedSet.add(per[0] * BASE + per[1]);
        }
        return check(s, t) && check(t, s);
    }

    public boolean check(int[] s, int[] t) {
        Set<Long> vis = new HashSet<>();
        Deque<int[]> d = new ArrayDeque<>();
        d.addLast(s);
        vis.add(s[0] * BASE + s[1]);
        while (!d.isEmpty() && vis.size() <= MAX) {
            int[] poll = d.poll();
            int x = poll[0], y = poll[1];
            if (x == t[0] && y == t[1]) return true;
            for (int[] di : dir) {
                int nx = x + di[0], ny = y + di[1];
                if (nx < 0 || nx >= EDGE || ny < 0 || ny >= EDGE) continue;
                long hash = nx * BASE + ny;
                if (vis.contains(hash) || blockedSet.contains(nx * BASE + ny)) continue;
                d.addLast(new int[]{nx, ny});
                vis.add(hash);
            }
        }
        return vis.size() > MAX;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
