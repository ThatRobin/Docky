# Docky - Fabric Mod

Docky is a Fabric mod for Minecraft Java Edition. It serves as a custom data management tool, which allows you to register, handle and manage custom data types within your Minecraft mod. Docky also provides mechanisms for serializing and deserializing these data types, creating a more manageable and streamlined mod development process.

## Main Features

1. **Docky Registry**: Registers unique `DockyEntry` objects to prevent duplication.
2. **Docky Generator**: An interface to assist in writing data to paths in the Minecraft file system.
3. **Docky Data Provider**: An abstract class that provides data output methods.
4. **Docky Data Type Contents Page Provider**: Generates a Markdown page with a list of data types.
5. **Docky MkDocs Provider**: Creates a YAML file for MkDocs, a static site generator focused on project documentation.
6. **Docky Requirements Provider**: Generates a list of required dependencies for the project.

## Usage

In order to use Docky in your own project, clone this repository into your local machine. Then, refer to the individual classes and their methods to use the services provided by Docky.

For more specific instructions, refer to the comments within each file. Most methods are annotated with useful comments providing insight into what each specific part of the code does.

## Contribute

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)

## Acknowledgments

This project was made possible thanks to the extensive work of the Minecraft modding community, and specifically the Fabric modding platform.
