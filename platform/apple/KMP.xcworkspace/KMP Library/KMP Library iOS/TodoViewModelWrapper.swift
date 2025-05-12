import SwiftUI
import SharedDomain

class TodoViewModelWrapper: ObservableObject {
    private let viewModel: TodoViewModel
    private var subscription: Closeable? = nil

    @Published var state: TodoUIState

    init() {
        self.viewModel = TodoViewModel()
        self.state = viewModel.state.value

        self.subscription = viewModel.state.watch { [weak self] newState in
            DispatchQueue.main.async {
                self?.state = newState
            }
        }
    }

    func getAllTodo() {
        viewModel.getAllTodo()
    }

    func createTodo(title: String, completed: Bool) {
        viewModel.createTodo(title: title, completed: completed)
    }

    func updateTodo(id: Int32, title: String, completed: Bool) {
        viewModel.updateTodo(id: id, title: title, completed: completed)
    }

    func deleteTodo(id: Int32) {
        viewModel.deleteTodo(id: id)
    }

    deinit {
        subscription?.close()
    }
}
