# LEARN
- Models choices keyword 활용
- django admin page 노출 설정 방법

# ISSUE
`users.User.avatar: (fields.E210) Cannot use ImageField because Pillow is not installed.`
- django 소스 에서 `Pillow` 를 설치를 체크 하는 로직이 존재;;
```python
    def _check_image_library_installed(self):
        try:
            from PIL import Image  # NOQA
        except ImportError:
            return [
                checks.Error(
                    "Cannot use ImageField because Pillow is not installed.",
                    hint=(
                        "Get Pillow at https://pypi.org/project/Pillow/ "
                        'or run command "python -m pip install Pillow".'
                    ),
                    obj=self,
                    id="fields.E210",
                )
            ]
```