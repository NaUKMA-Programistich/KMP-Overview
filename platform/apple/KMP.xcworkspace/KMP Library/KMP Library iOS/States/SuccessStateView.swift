import SwiftUI
import SharedDomain

struct SuccessStateView: View {
    let todos: [Todo]
    let onUpdate: (Int32, String, Bool) -> Void
    let onDelete: (Int32) -> Void

    @State private var editingTodo: Todo? = nil

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 8) {
                ForEach(todos, id: \.id) { todo in
                    TodoItemView(
                        todo: todo,
                        onUpdate: onUpdate,
                        onDelete: onDelete,
                        onEdit: { self.editingTodo = todo }
                    )
                }
            }
            .padding(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
        }
        .sheet(item: $editingTodo) { todo in
            TodoEditView(
                todo: todo,
                onUpdate: onUpdate,
                onDelete: onDelete,
                onDismiss: { editingTodo = nil }
            )
        }
    }
}
