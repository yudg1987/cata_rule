module.exports = {
  extends: "plugin:vue/essential",
  env: {
    // 你的环境变量（包含多个预定义的全局变量）
    //
    browser: true,
    node: true,
    es6: true
  },
  globals: {
    // 你的全局变量（设置为 false 表示它不允许被重新赋值）
    //
    // myGlobal: false
  },
  rules: {
    // 自定义你的规则
    "array-callback-return": 1,
		"indent": [1, 2],
		"quotes": [1, "double"],
    "comma-dangle": [2, 'never'],
    "no-undef": 0,
		"no-dupe-args": 2,
		"no-dupe-keys": 2,
		"no-alert": 1,
		"no-multi-spaces": 1,
		"array-bracket-spacing": [2, "never"]
  },
  parserOptions: {
    parser: "babel-eslint",
    sourceType: 'module'
  }
};