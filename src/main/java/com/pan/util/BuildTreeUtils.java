package com.pan.util;

import com.pan.query.Tree;

import java.util.*;

public class BuildTreeUtils {
	/**
	 * build
	 * @param nodes 属性
	 * @param <T>
	 * @return 返回数据
	 */
	public static <T> Tree<T> build(List<Tree<T>> nodes) {

		if (nodes == null) {
			return null;
		}

		List<Tree<T>> topNodes = new ArrayList<>();
		//遍历节点
		for (Tree<T> children : nodes) {
			String pid = children.getParentId();
			//顶级节点判断
			if (pid == null || String.valueOf(MacroelementUtils.ZERO).equals(pid)) {
				topNodes.add(children);
				continue;
			}
			//遍历封装对象
			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setChildren(true);
					continue;
				}
			}
		}

		Tree<T> root = new Tree<T>();
		if (topNodes.size() == 1) {
			root = topNodes.get(0);
		} else {
			root.setId("-1");
			root.setParentId("");
			root.setHasParent(false);
			root.setChildren(true);
			root.setChecked(true);
			root.setChildren(topNodes);
			root.setText("顶级节点");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			root.setState(state);
		}
		return root;
	}
}
